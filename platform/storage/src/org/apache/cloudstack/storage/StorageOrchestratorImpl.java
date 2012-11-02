/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.cloudstack.storage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.cloudstack.platform.subsystem.api.storage.DataObjectBackupStorageOperationState;
import org.apache.cloudstack.platform.subsystem.api.storage.DataStore;
import org.apache.cloudstack.platform.subsystem.api.storage.StorageProvider;
import org.apache.cloudstack.platform.subsystem.api.storage.TemplateProfile;
import org.apache.cloudstack.platform.subsystem.api.storage.VolumeProfile;
import org.apache.cloudstack.platform.subsystem.api.storage.VolumeStrategy;
import org.apache.cloudstack.storage.image.ImageManager;
import org.apache.cloudstack.storage.manager.BackupStorageManager;
import org.apache.cloudstack.storage.manager.SecondaryStorageManager;
import org.apache.cloudstack.storage.volume.VolumeManager;
import org.apache.log4j.Logger;

import com.cloud.deploy.DeploymentPlan;
import com.cloud.exception.InvalidParameterValueException;
import com.cloud.offering.DiskOffering;
import com.cloud.storage.DiskOfferingVO;
import com.cloud.storage.StoragePool;
import com.cloud.storage.Volume;
import com.cloud.storage.VolumeVO;
import com.cloud.storage.dao.DiskOfferingDao;
import com.cloud.storage.dao.StoragePoolDao;
import com.cloud.storage.dao.VMTemplateDao;
import com.cloud.storage.dao.VolumeDao;
import com.cloud.storage.dao.VolumeHostDao;
import com.cloud.template.VirtualMachineTemplate;
import com.cloud.utils.db.DB;
import com.cloud.utils.db.Transaction;
import com.cloud.utils.exception.CloudRuntimeException;
import com.cloud.utils.fsm.NoTransitionException;
import com.cloud.vm.VirtualMachine;
import com.cloud.vm.dao.VMInstanceDao;

public class StorageOrchestratorImpl implements StorageOrchestrator {
	private static final Logger s_logger = Logger.getLogger(StorageOrchestratorImpl.class);
	@Inject
	StoragePoolDao _storagePoolDao;
	@Inject
	StorageProviderManager _spManager;
	@Inject
	VolumeDao _volumeDao;
	@Inject
	VMInstanceDao _vmDao;
	@Inject
	DiskOfferingDao _diskOfferingDao;
	@Inject
	VolumeHostDao _volumeHostDao;
	@Inject
	StorageProviderManager _storageProviderMgr;
	@Inject
	VolumeManager _volumeMgr;
	@Inject
	SecondaryStorageManager _secondaryStorageMgr;
	@Inject
	ImageManager _templateMgr;
	@Inject
	VMTemplateDao _templateDao;
	
	@DB
	protected Volume copyVolumeFromBackupStorage(VolumeVO volume, DataStore destStore, String reservationId) throws NoTransitionException {
		DataStore ds = _secondaryStorageMgr.getStore(volume);
		if (!ds.contains(volume)) {
			throw new CloudRuntimeException("volume: " + volume + "doesn't exist on backup storage");
		}
		
		VolumeProfile vp = ds.prepareVolume(volume, destStore);
		
		VolumeStrategy vs = destStore.getVolumeStrategy();

		Transaction txn = Transaction.currentTxn();
		volume.setReservationId(reservationId);
		_volumeMgr.processEvent(volume, Volume.Event.CopyRequested);
		VolumeVO destVolume = _volumeMgr.allocateDuplicateVolume(volume);
		destVolume = _volumeMgr.processEvent(destVolume, Volume.Event.CreateRequested);
		txn.commit();
		
		vs.copyVolumeFromBackup(vp, destVolume);
		
		txn.start();
		volume = _volumeMgr.processEvent(volume, Volume.Event.OperationSucceeded);
		destVolume = _volumeMgr.processEvent(destVolume, Volume.Event.OperationSucceeded);
		txn.commit();
		
		return destVolume;
	}
	
	@DB
	protected Volume migrateVolume(VolumeVO volume, DataStore srcStore, DataStore destStore, String reservationId) throws NoTransitionException {
		Transaction txn = Transaction.currentTxn();
		txn.start();
		volume.setReservationId(reservationId);
		volume = _volumeMgr.processEvent(volume, Volume.Event.MigrationRequested);
		Volume destVolume = _volumeMgr.allocateDuplicateVolume(volume);
		destVolume = _volumeMgr.processEvent(destVolume, Volume.Event.CreateRequested);
		txn.commit();
		
		VolumeStrategy vs = srcStore.getVolumeStrategy();
		vs.migrateVolume(volume, destVolume, destStore);
		
		txn.start();
		volume = _volumeMgr.processEvent(volume, Volume.Event.OperationSucceeded);
		destVolume = _volumeMgr.processEvent(destVolume, Volume.Event.OperationSucceeded);
		txn.commit();
		
		volume = _volumeMgr.processEvent(volume, Volume.Event.DestroyRequested);
		
		vs.deleteVolume(volume);
		
		_volumeMgr.processEvent(volume, Volume.Event.OperationSucceeded);
		return destVolume;
	}
	
	@DB
	protected Volume recreateVolume(VolumeVO srcVolume, DataStore destStore, String reservationId) throws NoTransitionException {
		Transaction txn = Transaction.currentTxn();
		txn.start();
		srcVolume.setReservationId(reservationId);
		srcVolume = _volumeMgr.processEvent(srcVolume, Volume.Event.CopyRequested);
		Volume destVolume = _volumeMgr.allocateDuplicateVolume(srcVolume);
		destVolume = _volumeMgr.processEvent(destVolume, Volume.Event.CreateRequested);
		txn.commit();
		
		DataStore srcStore = _storageProviderMgr.getDataStore(srcVolume.getPoolId());
		VolumeStrategy vs = srcStore.getVolumeStrategy();
		
		vs.migrateVolume(srcVolume, destVolume, destStore);
		
		txn.start();
		srcVolume = _volumeMgr.processEvent(srcVolume, Volume.Event.OperationSucceeded);
		destVolume = _volumeMgr.processEvent(destVolume, Volume.Event.OperationSucceeded);
		txn.commit();
		
		srcVolume = _volumeMgr.processEvent(srcVolume, Volume.Event.DestroyRequested);
		
		vs.deleteVolume(srcVolume);
		
		_volumeMgr.processEvent(srcVolume, Volume.Event.OperationSucceeded);
		
		return destVolume;
	}
	
	protected Volume createVolumeOnStorage(Volume volume, DataStore destStore, String reservationId) throws NoTransitionException {
		VolumeStrategy vs = destStore.getVolumeStrategy();
		volume.setReservationId(reservationId);
		volume = _volumeMgr.processEvent(volume, Volume.Event.CreateRequested);
		
		if (volume.getTemplateId() != null) {
			DataStore ds = _secondaryStorageMgr.getImageStore(destStore);
			TemplateProfile tp = ds.prepareTemplate(volume.getTemplateId(), destStore);
			if (!destStore.contains(tp)) {
				tp = _templateMgr.AssociateTemplateStoragePool(tp, destStore);
				tp  = destStore.getTemplateStrategy().install(tp);
			} else {
				tp = destStore.getTemplateStrategy().get(tp.getId());
			}
			volume = vs.createVolumeFromBaseTemplate(volume, tp);
		} else {
			volume = vs.createDataVolume(volume);
		}
		
		volume = _volumeMgr.processEvent(volume, Volume.Event.OperationSucceeded);
		return volume;
	}
	
	@DB
	protected void prepareVolumes(List<VolumeVO> vols, Long destPoolId, String reservationId) throws NoTransitionException {
		DataStore destStore = null;
		if (destPoolId != null) {
			destStore = _storageProviderMgr.getDataStore(destPoolId);
		}
		
		for (VolumeVO volume : vols) {
			if (volume.getPoolId() == null && destStore == null) {
				throw new CloudRuntimeException("Volume has no pool associate and also no storage pool assigned in DeployDestination, Unable to create.");
			}
			if (destStore == null) {
				continue;
			}

			DataStore srcStore = _storageProviderMgr.getDataStore(volume.getPoolId());
			boolean needToCreateVolume = false;
			boolean needToRecreateVolume = false;
			boolean needToMigrateVolume = false;
			boolean needToCopyFromSec = false;

			Volume.State state = volume.getState();
			if (state == Volume.State.Allocated) {
				needToCreateVolume = true;
			} else if (state == Volume.State.UploadOp) {
				needToCopyFromSec = true;
			} else if (destStore.getId() != srcStore.getId()) {
				if (s_logger.isDebugEnabled()) {
					s_logger.debug("Mismatch in storage pool " + destStore.getId() + " assigned by deploymentPlanner and the one associated with volume " + volume);
				}
				
				if (volume.isRecreatable()) {
					needToRecreateVolume = true;
				} else {
					if (Volume.Type.ROOT == volume.getVolumeType()) {
						needToMigrateVolume = true;
					} else {
						if (destStore.getCluterId() != srcStore.getCluterId()) {
							needToMigrateVolume = true;
						} else if (!srcStore.isSharedStorage() && srcStore.getId() != destStore.getId()) {
							needToMigrateVolume = true;
						} else {
							continue;
						}
					}	
				}
			} else {
				continue;
			}
			
			
			if (needToCreateVolume) {
				createVolumeOnStorage(volume, destStore, reservationId);
			} else if (needToMigrateVolume) {
				migrateVolume(volume, srcStore, destStore, reservationId);
			} else if (needToCopyFromSec) {
				copyVolumeFromBackupStorage(volume, destStore, reservationId);
			} else if (needToRecreateVolume) {
				recreateVolume(volume, destStore, reservationId);
			}
		}
	}
	
	public void prepare(long vmId, DeploymentPlan plan, String reservationId) {
        VirtualMachine vm = _vmDao.findById(vmId);
       

        List<VolumeVO> vols = _volumeDao.findUsableVolumesForInstance(vm.getId());
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Prepare " + vols.size() + " volumes for " + vm.getInstanceName());
        }
        
        try {
        	prepareVolumes(vols, plan.getPoolId(), reservationId);
        } catch (NoTransitionException e) {
        	s_logger.debug("Failed to prepare volume: " + e.toString());
        }
    }


	public void release(long vmId, String reservationId) {
		// TODO Auto-generated method stub
		
	}

	public void destroy(List<Long> disks, String reservationId) {
		// TODO Auto-generated method stub
		
	}

	public void cancel(String reservationId) {
		// TODO Auto-generated method stub
		
	}

	public void prepareAttachDiskToVM(long diskId, long vmId, String reservationId) {
		VirtualMachine vm = _vmDao.findById(vmId);
		
		if (vm == null || vm.getState() != VirtualMachine.State.Running) {
			return;
		}
		
		VolumeVO volume = _volumeDao.findById(diskId);
		if (volume.getInstanceId() != null) {
			if (volume.getInstanceId() != vmId) {
				throw new InvalidParameterValueException("Volume " + volume + "already attached to " + volume.getInstanceId());
			} else {
				return;
			}
		}
		
		List<VolumeVO> vols = new ArrayList<VolumeVO>();
		vols.add(volume);
		
		List<VolumeVO> rootDisks = _volumeDao.findByInstanceAndType(vmId, Volume.Type.ROOT);
		VolumeVO rootDisk = rootDisks.get(0);
		try {
			prepareVolumes(vols, rootDisk.getPoolId(), reservationId);
		} catch (NoTransitionException e) {
			s_logger.debug("Failed to prepare volume: " + volume + ", due to" + e.toString());
			throw new CloudRuntimeException(e.toString());
		}
		
		volume = _volumeDao.findById(diskId);
		volume.setInstanceId(vmId);
		_volumeDao.update(volume.getId(), volume);
	}
}

// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package com.cloud.vpc;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Local;
import javax.inject.Inject;
import javax.naming.ConfigurationException;
import javax.naming.NamingException;

import com.cloud.api.commands.CreateDiskOfferingCmd;
import com.cloud.api.commands.CreateNetworkOfferingCmd;
import com.cloud.api.commands.CreateServiceOfferingCmd;
import com.cloud.api.commands.CreateVlanIpRangeCmd;
import com.cloud.api.commands.CreateZoneCmd;
import com.cloud.api.commands.DeleteDiskOfferingCmd;
import com.cloud.api.commands.DeleteNetworkOfferingCmd;
import com.cloud.api.commands.DeletePodCmd;
import com.cloud.api.commands.DeleteServiceOfferingCmd;
import com.cloud.api.commands.DeleteVlanIpRangeCmd;
import com.cloud.api.commands.DeleteZoneCmd;
import com.cloud.api.commands.LDAPConfigCmd;
import com.cloud.api.commands.LDAPRemoveCmd;
import com.cloud.api.commands.ListNetworkOfferingsCmd;
import com.cloud.api.commands.UpdateCfgCmd;
import com.cloud.api.commands.UpdateDiskOfferingCmd;
import com.cloud.api.commands.UpdateNetworkOfferingCmd;
import com.cloud.api.commands.UpdatePodCmd;
import com.cloud.api.commands.UpdateServiceOfferingCmd;
import com.cloud.api.commands.UpdateZoneCmd;
import com.cloud.configuration.Configuration;
import com.cloud.configuration.ConfigurationManager;
import com.cloud.configuration.ConfigurationService;
import com.cloud.dc.ClusterVO;
import com.cloud.dc.DataCenter;
import com.cloud.dc.DataCenter.NetworkType;
import com.cloud.dc.DataCenterVO;
import com.cloud.dc.HostPodVO;
import com.cloud.dc.Pod;
import com.cloud.dc.Vlan;
import com.cloud.exception.ConcurrentOperationException;
import com.cloud.exception.InsufficientCapacityException;
import com.cloud.exception.InvalidParameterValueException;
import com.cloud.exception.ResourceAllocationException;
import com.cloud.exception.ResourceUnavailableException;
import com.cloud.network.Network.Capability;
import com.cloud.network.Network.GuestType;
import com.cloud.network.Network.Provider;
import com.cloud.network.Network.Service;
import com.cloud.network.Networks.TrafficType;
import com.cloud.offering.DiskOffering;
import com.cloud.offering.NetworkOffering;
import com.cloud.offering.NetworkOffering.Availability;
import com.cloud.offering.ServiceOffering;
import com.cloud.offerings.NetworkOfferingVO;
import com.cloud.offerings.dao.NetworkOfferingDao;
import com.cloud.org.Grouping.AllocationState;
import com.cloud.service.ServiceOfferingVO;
import com.cloud.storage.DiskOfferingVO;
import com.cloud.user.Account;
import com.cloud.utils.component.Manager;
import com.cloud.vm.VirtualMachine.Type;

@Local(value = { ConfigurationManager.class, ConfigurationService.class })
public class MockConfigurationManagerImpl implements ConfigurationManager, ConfigurationService, Manager{
    @Inject
    NetworkOfferingDao _ntwkOffDao;

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#updateConfiguration(com.cloud.api.commands.UpdateCfgCmd)
     */
    @Override
    public Configuration updateConfiguration(UpdateCfgCmd cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#createServiceOffering(com.cloud.api.commands.CreateServiceOfferingCmd)
     */
    @Override
    public ServiceOffering createServiceOffering(CreateServiceOfferingCmd cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#updateServiceOffering(com.cloud.api.commands.UpdateServiceOfferingCmd)
     */
    @Override
    public ServiceOffering updateServiceOffering(UpdateServiceOfferingCmd cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#deleteServiceOffering(com.cloud.api.commands.DeleteServiceOfferingCmd)
     */
    @Override
    public boolean deleteServiceOffering(DeleteServiceOfferingCmd cmd) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#updateDiskOffering(com.cloud.api.commands.UpdateDiskOfferingCmd)
     */
    @Override
    public DiskOffering updateDiskOffering(UpdateDiskOfferingCmd cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#deleteDiskOffering(com.cloud.api.commands.DeleteDiskOfferingCmd)
     */
    @Override
    public boolean deleteDiskOffering(DeleteDiskOfferingCmd cmd) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#createDiskOffering(com.cloud.api.commands.CreateDiskOfferingCmd)
     */
    @Override
    public DiskOffering createDiskOffering(CreateDiskOfferingCmd cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#createPod(long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Pod createPod(long zoneId, String name, String startIp, String endIp, String gateway, String netmask, String allocationState) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#editPod(com.cloud.api.commands.UpdatePodCmd)
     */
    @Override
    public Pod editPod(UpdatePodCmd cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#deletePod(com.cloud.api.commands.DeletePodCmd)
     */
    @Override
    public boolean deletePod(DeletePodCmd cmd) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#createZone(com.cloud.api.commands.CreateZoneCmd)
     */
    @Override
    public DataCenter createZone(CreateZoneCmd cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#editZone(com.cloud.api.commands.UpdateZoneCmd)
     */
    @Override
    public DataCenter editZone(UpdateZoneCmd cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#deleteZone(com.cloud.api.commands.DeleteZoneCmd)
     */
    @Override
    public boolean deleteZone(DeleteZoneCmd cmd) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#createVlanAndPublicIpRange(com.cloud.api.commands.CreateVlanIpRangeCmd)
     */
    @Override
    public Vlan createVlanAndPublicIpRange(CreateVlanIpRangeCmd cmd) throws InsufficientCapacityException, ConcurrentOperationException, ResourceUnavailableException, ResourceAllocationException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#markDefaultZone(java.lang.String, long, long)
     */
    @Override
    public Account markDefaultZone(String accountName, long domainId, long defaultZoneId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#deleteVlanIpRange(com.cloud.api.commands.DeleteVlanIpRangeCmd)
     */
    @Override
    public boolean deleteVlanIpRange(DeleteVlanIpRangeCmd cmd) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#createNetworkOffering(com.cloud.api.commands.CreateNetworkOfferingCmd)
     */
    @Override
    public NetworkOffering createNetworkOffering(CreateNetworkOfferingCmd cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#updateNetworkOffering(com.cloud.api.commands.UpdateNetworkOfferingCmd)
     */
    @Override
    public NetworkOffering updateNetworkOffering(UpdateNetworkOfferingCmd cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#searchForNetworkOfferings(com.cloud.api.commands.ListNetworkOfferingsCmd)
     */
    @Override
    public List<? extends NetworkOffering> searchForNetworkOfferings(ListNetworkOfferingsCmd cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#deleteNetworkOffering(com.cloud.api.commands.DeleteNetworkOfferingCmd)
     */
    @Override
    public boolean deleteNetworkOffering(DeleteNetworkOfferingCmd cmd) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#getNetworkOffering(long)
     */
    @Override
    public NetworkOffering getNetworkOffering(long id) {
        return _ntwkOffDao.findById(id);
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#getNetworkOfferingNetworkRate(long)
     */
    @Override
    public Integer getNetworkOfferingNetworkRate(long networkOfferingId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#getVlanAccount(long)
     */
    @Override
    public Account getVlanAccount(long vlanId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#listNetworkOfferings(com.cloud.network.Networks.TrafficType, boolean)
     */
    @Override
    public List<? extends NetworkOffering> listNetworkOfferings(TrafficType trafficType, boolean systemOnly) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#getZone(long)
     */
    @Override
    public DataCenter getZone(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#getServiceOffering(long)
     */
    @Override
    public ServiceOffering getServiceOffering(long serviceOfferingId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#getDefaultPageSize()
     */
    @Override
    public Long getDefaultPageSize() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#getServiceOfferingNetworkRate(long)
     */
    @Override
    public Integer getServiceOfferingNetworkRate(long serviceOfferingId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#getDiskOffering(long)
     */
    @Override
    public DiskOffering getDiskOffering(long diskOfferingId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#updateLDAP(com.cloud.api.commands.LDAPConfigCmd)
     */
    @Override
    public boolean updateLDAP(LDAPConfigCmd cmd) throws NamingException {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#removeLDAP(com.cloud.api.commands.LDAPRemoveCmd)
     */
    @Override
    public boolean removeLDAP(LDAPRemoveCmd cmd) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationService#isOfferingForVpc(com.cloud.offering.NetworkOffering)
     */
    @Override
    public boolean isOfferingForVpc(NetworkOffering offering) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.utils.component.Manager#configure(java.lang.String, java.util.Map)
     */
    @Override
    public boolean configure(String name, Map<String, Object> params) throws ConfigurationException {
        // TODO Auto-generated method stub
        return true;
    }

    /* (non-Javadoc)
     * @see com.cloud.utils.component.Manager#start()
     */
    @Override
    public boolean start() {
        // TODO Auto-generated method stub
        return true;
    }

    /* (non-Javadoc)
     * @see com.cloud.utils.component.Manager#stop()
     */
    @Override
    public boolean stop() {
        // TODO Auto-generated method stub
        return true;
    }

    /* (non-Javadoc)
     * @see com.cloud.utils.component.Manager#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#updateConfiguration(long, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void updateConfiguration(long userId, String name, String category, String value) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#createServiceOffering(long, boolean, com.cloud.vm.VirtualMachine.Type, java.lang.String, int, int, int, java.lang.String, boolean, boolean, boolean, java.lang.String, java.lang.Long, java.lang.String, java.lang.Integer)
     */
    @Override
    public ServiceOfferingVO createServiceOffering(long userId, boolean isSystem, Type vm_typeType, String name, int cpu, int ramSize, int speed, String displayText, boolean localStorageRequired, boolean offerHA,
            boolean limitResourceUse, String tags, Long domainId, String hostTag, Integer networkRate) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#createPod(long, java.lang.String, long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
     */
    @Override
    public HostPodVO createPod(long userId, String podName, long zoneId, String gateway, String cidr, String startIp, String endIp, String allocationState, boolean skipGatewayOverlapCheck) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#deleteVlanAndPublicIpRange(long, long, com.cloud.user.Account)
     */
    @Override
    public boolean deleteVlanAndPublicIpRange(long userId, long vlanDbId, Account caller) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#csvTagsToList(java.lang.String)
     */
    @Override
    public List<String> csvTagsToList(String tags) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#listToCsvTags(java.util.List)
     */
    @Override
    public String listToCsvTags(List<String> tags) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#checkZoneAccess(com.cloud.user.Account, com.cloud.dc.DataCenter)
     */
    @Override
    public void checkZoneAccess(Account caller, DataCenter zone) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#checkDiskOfferingAccess(com.cloud.user.Account, com.cloud.offering.DiskOffering)
     */
    @Override
    public void checkDiskOfferingAccess(Account caller, DiskOffering dof) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#createNetworkOffering(java.lang.String, java.lang.String, com.cloud.network.Networks.TrafficType, java.lang.String, boolean, com.cloud.offering.NetworkOffering.Availability, java.lang.Integer, java.util.Map, boolean, com.cloud.network.Network.GuestType, boolean, java.lang.Long, boolean, java.util.Map, boolean)
     */
    @Override
    public NetworkOfferingVO createNetworkOffering(String name, String displayText, TrafficType trafficType, String tags, boolean specifyVlan, Availability availability, Integer networkRate,
            Map<Service, Set<Provider>> serviceProviderMap, boolean isDefault, GuestType type, boolean systemOnly, Long serviceOfferingId, boolean conserveMode,
            Map<Service, Map<Capability, String>> serviceCapabilityMap, boolean specifyIpRanges) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#createVlanAndPublicIpRange(long, long, long, boolean, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.cloud.user.Account)
     */
    @Override
    public Vlan createVlanAndPublicIpRange(long zoneId, long networkId, long physicalNetworkId, boolean forVirtualNetwork, Long podId, String startIP, String endIP, String vlanGateway, String vlanNetmask, String vlanId,
            Account vlanOwner) throws InsufficientCapacityException, ConcurrentOperationException, InvalidParameterValueException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#createDefaultSystemNetworks(long)
     */
    @Override
    public void createDefaultSystemNetworks(long zoneId) throws ConcurrentOperationException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#getPod(long)
     */
    @Override
    public HostPodVO getPod(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#getCluster(long)
     */
    @Override
    public ClusterVO getCluster(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#deleteAccountSpecificVirtualRanges(long)
     */
    @Override
    public boolean deleteAccountSpecificVirtualRanges(long accountId) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#editPod(long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Pod editPod(long id, String name, String startIp, String endIp, String gateway, String netmask, String allocationStateStr) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#checkPodCidrSubnets(long, java.lang.Long, java.lang.String)
     */
    @Override
    public void checkPodCidrSubnets(long zoneId, Long podIdToBeSkipped, String cidr) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#findPodAllocationState(com.cloud.dc.HostPodVO)
     */
    @Override
    public AllocationState findPodAllocationState(HostPodVO pod) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#findClusterAllocationState(com.cloud.dc.ClusterVO)
     */
    @Override
    public AllocationState findClusterAllocationState(ClusterVO cluster) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#cleanupTags(java.lang.String)
     */
    @Override
    public String cleanupTags(String tags) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#createDiskOffering(java.lang.Long, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, boolean, boolean)
     */
    @Override
    public DiskOfferingVO createDiskOffering(Long domainId, String name, String description, Long numGibibytes, String tags, boolean isCustomized, boolean localStorageRequired) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.configuration.ConfigurationManager#createZone(long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, com.cloud.dc.DataCenter.NetworkType, java.lang.String, java.lang.String, boolean, boolean)
     */
    @Override
    public DataCenterVO createZone(long userId, String zoneName, String dns1, String dns2, String internalDns1, String internalDns2, String guestCidr, String domain, Long domainId, NetworkType zoneType,
            String allocationState, String networkDomain, boolean isSecurityGroupEnabled, boolean isLocalStorageEnabled) {
        // TODO Auto-generated method stub
        return null;
    }


}

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

import javax.ejb.Local;
import javax.naming.ConfigurationException;

import com.cloud.api.commands.CreateVpnConnectionCmd;
import com.cloud.api.commands.CreateVpnCustomerGatewayCmd;
import com.cloud.api.commands.CreateVpnGatewayCmd;
import com.cloud.api.commands.DeleteVpnConnectionCmd;
import com.cloud.api.commands.DeleteVpnCustomerGatewayCmd;
import com.cloud.api.commands.DeleteVpnGatewayCmd;
import com.cloud.api.commands.ListVpnConnectionsCmd;
import com.cloud.api.commands.ListVpnCustomerGatewaysCmd;
import com.cloud.api.commands.ListVpnGatewaysCmd;
import com.cloud.api.commands.ResetVpnConnectionCmd;
import com.cloud.api.commands.UpdateVpnCustomerGatewayCmd;
import com.cloud.exception.NetworkRuleConflictException;
import com.cloud.exception.ResourceUnavailableException;
import com.cloud.network.Site2SiteCustomerGateway;
import com.cloud.network.Site2SiteVpnConnection;
import com.cloud.network.Site2SiteVpnConnectionVO;
import com.cloud.network.Site2SiteVpnGateway;
import com.cloud.network.vpn.Site2SiteVpnManager;
import com.cloud.network.vpn.Site2SiteVpnService;
import com.cloud.utils.Pair;
import com.cloud.utils.component.Manager;
import com.cloud.vm.DomainRouterVO;

@Local(value = { Site2SiteVpnManager.class, Site2SiteVpnService.class } )
public class MockSite2SiteVpnManagerImpl implements Site2SiteVpnManager, Site2SiteVpnService, Manager{

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnService#createVpnGateway(com.cloud.api.commands.CreateVpnGatewayCmd)
     */
    @Override
    public Site2SiteVpnGateway createVpnGateway(CreateVpnGatewayCmd cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnService#createCustomerGateway(com.cloud.api.commands.CreateVpnCustomerGatewayCmd)
     */
    @Override
    public Site2SiteCustomerGateway createCustomerGateway(CreateVpnCustomerGatewayCmd cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnService#startVpnConnection(long)
     */
    @Override
    public Site2SiteVpnConnection startVpnConnection(long id) throws ResourceUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnService#getVpnGateway(java.lang.Long)
     */
    @Override
    public Site2SiteVpnGateway getVpnGateway(Long vpnGatewayId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnService#createVpnConnection(com.cloud.api.commands.CreateVpnConnectionCmd)
     */
    @Override
    public Site2SiteVpnConnection createVpnConnection(CreateVpnConnectionCmd cmd) throws NetworkRuleConflictException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnService#deleteCustomerGateway(com.cloud.api.commands.DeleteVpnCustomerGatewayCmd)
     */
    @Override
    public boolean deleteCustomerGateway(DeleteVpnCustomerGatewayCmd deleteVpnCustomerGatewayCmd) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnService#deleteVpnGateway(com.cloud.api.commands.DeleteVpnGatewayCmd)
     */
    @Override
    public boolean deleteVpnGateway(DeleteVpnGatewayCmd deleteVpnGatewayCmd) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnService#deleteVpnConnection(com.cloud.api.commands.DeleteVpnConnectionCmd)
     */
    @Override
    public boolean deleteVpnConnection(DeleteVpnConnectionCmd deleteVpnConnectionCmd) throws ResourceUnavailableException {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnService#resetVpnConnection(com.cloud.api.commands.ResetVpnConnectionCmd)
     */
    @Override
    public Site2SiteVpnConnection resetVpnConnection(ResetVpnConnectionCmd resetVpnConnectionCmd) throws ResourceUnavailableException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnService#searchForCustomerGateways(com.cloud.api.commands.ListVpnCustomerGatewaysCmd)
     */
    @Override
    public Pair<List<? extends Site2SiteCustomerGateway>, Integer> searchForCustomerGateways(ListVpnCustomerGatewaysCmd listVpnCustomerGatewaysCmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnService#searchForVpnGateways(com.cloud.api.commands.ListVpnGatewaysCmd)
     */
    @Override
    public Pair<List<? extends Site2SiteVpnGateway>, Integer> searchForVpnGateways(ListVpnGatewaysCmd listVpnGatewaysCmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnService#searchForVpnConnections(com.cloud.api.commands.ListVpnConnectionsCmd)
     */
    @Override
    public Pair<List<? extends Site2SiteVpnConnection>, Integer> searchForVpnConnections(ListVpnConnectionsCmd listVpnConnectionsCmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnService#updateCustomerGateway(com.cloud.api.commands.UpdateVpnCustomerGatewayCmd)
     */
    @Override
    public Site2SiteCustomerGateway updateCustomerGateway(UpdateVpnCustomerGatewayCmd updateVpnCustomerGatewayCmd) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnManager#cleanupVpnConnectionByVpc(long)
     */
    @Override
    public boolean cleanupVpnConnectionByVpc(long vpcId) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnManager#cleanupVpnGatewayByVpc(long)
     */
    @Override
    public boolean cleanupVpnGatewayByVpc(long vpcId) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnManager#markDisconnectVpnConnByVpc(long)
     */
    @Override
    public void markDisconnectVpnConnByVpc(long vpcId) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnManager#getConnectionsForRouter(com.cloud.vm.DomainRouterVO)
     */
    @Override
    public List<Site2SiteVpnConnectionVO> getConnectionsForRouter(DomainRouterVO router) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.cloud.network.vpn.Site2SiteVpnManager#deleteCustomerGatewayByAccount(long)
     */
    @Override
    public boolean deleteCustomerGatewayByAccount(long accountId) {
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
     * @see com.cloud.network.vpn.Site2SiteVpnManager#reconnectDisconnectedVpnByVpc(java.lang.Long)
     */
    @Override
    public void reconnectDisconnectedVpnByVpc(Long vpcId) {
        // TODO Auto-generated method stub
        
    }

}

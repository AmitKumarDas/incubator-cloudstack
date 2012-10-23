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
package com.cloud.api.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cloud.api.ApiConstants;
import com.cloud.api.BaseListProjectAndAccountResourcesCmd;
import com.cloud.api.IdentityMapper;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.response.ListResponse;
import com.cloud.api.response.Site2SiteCustomerGatewayResponse;
import com.cloud.network.Site2SiteCustomerGateway;
import com.cloud.utils.Pair;

@Implementation(description="Lists site to site vpn customer gateways", responseObject=Site2SiteCustomerGatewayResponse.class)
public class ListVpnCustomerGatewaysCmd extends BaseListProjectAndAccountResourcesCmd {
    public static final Logger s_logger = Logger.getLogger (ListVpnCustomerGatewaysCmd.class.getName());

    private static final String s_name = "listvpncustomergatewaysresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @IdentityMapper(entityTableName="s2s_customer_gateway")
    @Parameter(name=ApiConstants.ID, type=CommandType.LONG, description="id of the customer gateway")
    private Long id;

    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    
    public Long getId() {
        return id;
    }

    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////

    @Override
    public String getCommandName() {
        return s_name;
    }

    @Override
    public void execute(){
        Pair<List<? extends Site2SiteCustomerGateway>, Integer> gws = _s2sVpnService.searchForCustomerGateways(this);
        ListResponse<Site2SiteCustomerGatewayResponse> response = new ListResponse<Site2SiteCustomerGatewayResponse>();
        List<Site2SiteCustomerGatewayResponse> gwResponses = new ArrayList<Site2SiteCustomerGatewayResponse>();
        for (Site2SiteCustomerGateway gw : gws.first()) {
            if (gw == null) {
                continue;
            }
        	Site2SiteCustomerGatewayResponse site2SiteCustomerGatewayRes = _responseGenerator.createSite2SiteCustomerGatewayResponse(gw);
        	site2SiteCustomerGatewayRes.setObjectName("vpncustomergateway");
            gwResponses.add(site2SiteCustomerGatewayRes);
        }
        
        response.setResponses(gwResponses, gws.second());
        response.setResponseName(getCommandName());
        this.setResponseObject(response);
    }
}

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
import com.cloud.api.response.InstanceGroupResponse;
import com.cloud.api.response.ListResponse;
import com.cloud.utils.Pair;
import com.cloud.vm.InstanceGroup;

@Implementation(description="Lists vm groups", responseObject=InstanceGroupResponse.class)
public class ListVMGroupsCmd extends BaseListProjectAndAccountResourcesCmd {
    public static final Logger s_logger = Logger.getLogger(ListVMGroupsCmd.class.getName());

    private static final String s_name = "listinstancegroupsresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @IdentityMapper(entityTableName="instance_group")
    @Parameter(name=ApiConstants.ID, type=CommandType.LONG, description="list instance groups by ID")
    private Long id;

    @Parameter(name=ApiConstants.NAME, type=CommandType.STRING, description="list instance groups by name")
    private String groupName;

    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
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
        Pair<List<? extends InstanceGroup>, Integer> groups = _mgr.searchForVmGroups(this);
        ListResponse<InstanceGroupResponse> response = new ListResponse<InstanceGroupResponse>();
        List<InstanceGroupResponse> responses = new ArrayList<InstanceGroupResponse>();
        for (InstanceGroup group : groups.first()) {
            InstanceGroupResponse groupResponse = _responseGenerator.createInstanceGroupResponse(group);
            groupResponse.setObjectName("instancegroup");
            responses.add(groupResponse);
        }

        response.setResponses(responses, groups.second());
        response.setResponseName(getCommandName());
        this.setResponseObject(response);
    }
}

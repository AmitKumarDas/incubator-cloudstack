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

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cloud.api.ApiConstants;
import com.cloud.api.BaseCmd;
import com.cloud.api.IdentityMapper;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.ServerApiException;
import com.cloud.api.BaseCmd.CommandType;
import com.cloud.api.response.ListResponse;
import com.cloud.api.response.TemplateResponse;
import com.cloud.async.AsyncJob;
import com.cloud.exception.ResourceAllocationException;
import com.cloud.template.VirtualMachineTemplate;
import com.cloud.user.UserContext;

@Implementation(description="Registers an existing template into the CloudStack cloud. ", responseObject=TemplateResponse.class)
public class RegisterTemplateCmd extends BaseCmd {
    public static final Logger s_logger = Logger.getLogger(RegisterTemplateCmd.class.getName());

    private static final String s_name = "registertemplateresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @Parameter(name=ApiConstants.BITS, type=CommandType.INTEGER, description="32 or 64 bits support. 64 by default")
    private Integer bits;

    @Parameter(name=ApiConstants.DISPLAY_TEXT, type=CommandType.STRING, required=true, description="the display text of the template. This is usually used for display purposes.", length=4096)
    private String displayText;

    @Parameter(name=ApiConstants.FORMAT, type=CommandType.STRING, required=true, description="the format for the template. Possible values include QCOW2, RAW, and VHD.")
    private String format;

    @Parameter(name=ApiConstants.HYPERVISOR, type=CommandType.STRING, required=true, description="the target hypervisor for the template")
    private String hypervisor;

    @Parameter(name=ApiConstants.IS_FEATURED, type=CommandType.BOOLEAN, description="true if this template is a featured template, false otherwise")
    private Boolean featured;

    @Parameter(name=ApiConstants.IS_PUBLIC, type=CommandType.BOOLEAN, description="true if the template is available to all accounts; default is true")
    private Boolean publicTemplate;

    @Parameter(name=ApiConstants.NAME, type=CommandType.STRING, required=true, description="the name of the template")
    private String templateName;

    @Parameter(name=ApiConstants.OS_TYPE_ID, type=CommandType.LONG, required=true, description="the ID of the OS Type that best represents the OS of this template.")
    @IdentityMapper(entityTableName="guest_os")
    private Long osTypeId;

    @Parameter(name=ApiConstants.PASSWORD_ENABLED, type=CommandType.BOOLEAN, description="true if the template supports the password reset feature; default is false")
    private Boolean passwordEnabled;
    
    @Parameter(name=ApiConstants.SSHKEY_ENABLED, type=CommandType.BOOLEAN, description="true if the template supports the sshkey upload feature; default is false")
    private Boolean sshKeyEnabled;

    @Parameter(name=ApiConstants.IS_EXTRACTABLE, type=CommandType.BOOLEAN, description="true if the template or its derivatives are extractable; default is false")
    private Boolean extractable;

    @Parameter(name=ApiConstants.REQUIRES_HVM, type=CommandType.BOOLEAN, description="true if this template requires HVM")
    private Boolean requiresHvm;

    @Parameter(name=ApiConstants.URL, type=CommandType.STRING, required=true, description="the URL of where the template is hosted. Possible URL include http:// and https://")
    private String url;

    @IdentityMapper(entityTableName="data_center")
    @Parameter(name=ApiConstants.ZONE_ID, type=CommandType.LONG, required=true, description="the ID of the zone the template is to be hosted on")
    private Long zoneId;

    @IdentityMapper(entityTableName="domain")
    @Parameter(name=ApiConstants.DOMAIN_ID, type=CommandType.LONG, description="an optional domainId. If the account parameter is used, domainId must also be used.")
    private Long domainId;

    @Parameter(name=ApiConstants.ACCOUNT, type=CommandType.STRING, description="an optional accountName. Must be used with domainId.")
    private String accountName;

    @Parameter(name=ApiConstants.CHECKSUM, type=CommandType.STRING, description="the MD5 checksum value of this template")
    private String checksum;

    @Parameter(name=ApiConstants.TEMPLATE_TAG, type=CommandType.STRING, description="the tag for this template.")
    private String templateTag;
    
    @IdentityMapper(entityTableName="projects")
    @Parameter(name=ApiConstants.PROJECT_ID, type=CommandType.LONG, description="Register template for the project")
    private Long projectId;
    
    @Parameter(name=ApiConstants.DETAILS, type=CommandType.MAP, description="Template details in key/value pairs.")
    protected Map details;

    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public Integer getBits() {
        return bits;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getFormat() {
        return format;
    }

    public String getHypervisor() {
        return hypervisor;
    }

    public Boolean isFeatured() {
        return featured;
    }

    public Boolean isPublic() {
        return publicTemplate;
    }

    public String getTemplateName() {
        return templateName;
    }

    public Long getOsTypeId() {
        return osTypeId;
    }

    public Boolean isPasswordEnabled() {
        return passwordEnabled;
    }
    
    public Boolean isSshKeyEnabled() {
        return sshKeyEnabled;
    }

    public Boolean isExtractable() {
        return extractable;
    }

    public Boolean getRequiresHvm() {
        return requiresHvm;
    }

    public String getUrl() {
        return url;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public Long getDomainId() {
        return domainId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getChecksum() {
        return checksum;
    }	

    public String getTemplateTag() {
        return templateTag;
    }
    
    public Map getDetails() {
    	if (details == null || details.isEmpty()) {
    		return null;
    	}
    	
    	Collection paramsCollection = details.values();
    	Map params = (Map) (paramsCollection.toArray())[0];
    	return params;
    }
    
    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////

    @Override
    public String getCommandName() {
        return s_name;
    }

    public AsyncJob.Type getInstanceType() {
        return AsyncJob.Type.Template;
    }

    @Override
    public long getEntityOwnerId() {
        Long accountId = finalyzeAccountId(accountName, domainId, projectId, true);
        if (accountId == null) {
            return UserContext.current().getCaller().getId();
        }
        
        return accountId;
    } 	

    @Override
    public void execute() throws ResourceAllocationException{
        try {
            VirtualMachineTemplate template = _templateService.registerTemplate(this);
            if (template != null){
                ListResponse<TemplateResponse> response = new ListResponse<TemplateResponse>();
                List<TemplateResponse> templateResponses = _responseGenerator.createTemplateResponses(template.getId(), zoneId, false);
                response.setResponses(templateResponses);
                response.setResponseName(getCommandName());              
                this.setResponseObject(response);
            } else {
                throw new ServerApiException(BaseCmd.INTERNAL_ERROR, "Failed to register template");
            }
        } catch (URISyntaxException ex1) {
            s_logger.info(ex1);
            throw new ServerApiException(BaseCmd.PARAM_ERROR, ex1.getMessage());
        }
    }
}
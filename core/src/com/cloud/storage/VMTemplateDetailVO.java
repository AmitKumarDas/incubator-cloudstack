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
package com.cloud.storage;

import org.apache.cloudstack.api.InternalIdentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vm_template_details")
public class VMTemplateDetailVO implements InternalIdentity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    
    @Column(name="template_id")
    private long templateId;
    
    @Column(name="name")
    private String name;
    
    @Column(name="value", length=1024)
    private String value;
	
    public VMTemplateDetailVO() {}
    
    public VMTemplateDetailVO(long templateId, String name, String value) {
    	this.templateId = templateId;
    	this.name = name;
    	this.value = value;
    }

	public long getId() {
		return id;
	}

	public long getTemplateId() {
		return templateId;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

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
(function(cloudStack) {
  cloudStack.sections.events = {
    title: 'label.menu.events',
    id: 'events',
    sectionSelect: {
      preFilter: function(args) {
        if(isAdmin())
          return ["events", "alerts"];
        else
          return ["events"];
      },
      label: 'label.select-view'
    },
    sections: {
      events: {
        type: 'select',
        title: 'label.menu.events',
        listView: {
          id: 'events',
          label: 'label.menu.events',
          fields: {            
            description: { label: 'label.description' },
						level: { label: 'label.level' },
            domain: { label: 'label.domain' },
						account: { label: 'label.account' },
            created: { label: 'label.date', converter: cloudStack.converters.toLocalDate }
          },
										
					advSearchFields: {	
            level: {
						  label: 'label.level',
							select: function(args) {
							  args.response.success({
									data: [
									  {id: '', description: ''}, 
									  {id: 'INFO', description: 'INFO'}, 
										{id: 'WARN', description: 'WARN'}, 
										{id: 'ERROR', description: 'ERROR'}
									]
								});
							}
						},					
						
						domainid: {					
							label: 'Domain',					
							select: function(args) {
								if(isAdmin() || isDomainAdmin()) {
									$.ajax({
										url: createURL('listDomains'),
										data: { 
											listAll: true,
											details: 'min'
										},
										success: function(json) {
											var array1 = [{id: '', description: ''}];
											var domains = json.listdomainsresponse.domain;
											if(domains != null && domains.length > 0) {
												for(var i = 0; i < domains.length; i++) {
													array1.push({id: domains[i].id, description: domains[i].path});
												}
											}
											args.response.success({
												data: array1
											});
										}
									});
								}
								else {
									args.response.success({
										data: null
									});
								}
							},
							isHidden: function(args) {
								if(isAdmin() || isDomainAdmin())
									return false;
								else
									return true;
							}
						},		
						
						account: { 
							label: 'Account',
							isHidden: function(args) {
								if(isAdmin() || isDomainAdmin())
									return false;
								else
									return true;
							}			
						}
            /*
						,
            startdate: {
              label: 'Start Date',
							isDatepicker: true
            },
            enddate: {
              label: 'End Date',
							isDatepicker: true
            }				
            */						
					},						
					
          dataProvider: function(args) {					  
						var data = {};
						listViewDataProvider(args, data);						
						
            $.ajax({
              url: createURL('listEvents'),
              data: data,              
              success: function(json) {
                var items = json.listeventsresponse.event;
                args.response.success({data:items});
              }
            });
          },
					detailView: {
            name: 'label.details',
            tabs: {
              details: {
                title: 'label.details',
                fields: [
                  {
									  description: { label: 'label.description' },
										state: { label: 'label.state' },
									  level: { label: 'label.level' },
                    type: { label: 'label.type' },										                
										domain: { label: 'label.domain' },
										account: { label: 'label.account' },
										username: { label: 'label.initiated.by' },
                    created: { label: 'label.date', converter: cloudStack.converters.toLocalDate },
										id: { label: 'label.id' }
                  }
                ],
                dataProvider: function(args) {								  
									$.ajax({
										url: createURL("listEvents&id=" + args.context.events[0].id),
										dataType: "json",
										async: true,
										success: function(json) {
											var item = json.listeventsresponse.event[0];
											args.response.success({data: item});
										}
									});									
								}
              }
            }
          }
        }
      },
      alerts: {
        type: 'select',
        title: 'label.menu.alerts',
        listView: {
          id: 'alerts',
          label: 'label.menu.alerts',
          fields: {
            description: { label: 'label.description' },
            sent: { label: 'label.date', converter: cloudStack.converters.toLocalDate }
          },
          dataProvider: function(args) {
					  var data = {};
						listViewDataProvider(args, data);		
					
            $.ajax({
              url: createURL('listAlerts'),
              data: data,
              async: true,
              success: function(json) {
                var items = json.listalertsresponse.alert;
                args.response.success({data:items});
              }
            });
          },
          detailView: {
            name: 'Alert details',
            tabs: {
              details: {
                title: 'label.details',
                fields: [
                  {
                    id: { label: 'ID' },
                    description: { label: 'label.description' },
                    sent: { label: 'label.date', converter: cloudStack.converters.toLocalDate }
                  }
                ],
                dataProvider: function(args) {								  
									$.ajax({
										url: createURL("listAlerts&id=" + args.context.alerts[0].id),
										dataType: "json",
										async: true,
										success: function(json) {
											var item = json.listalertsresponse.alert[0];
											args.response.success({data: item});
										}
									});															 
								}
              }
            }
          }
        }
      }
    }
  };
})(cloudStack);

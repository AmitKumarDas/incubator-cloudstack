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
package org.apache.cloudstack.framework.client;

import org.apache.cloudstack.framework.serializer.MessageSerializer;
import org.apache.cloudstack.framework.transport.TransportEndpoint;
import org.apache.cloudstack.framework.transport.TransportEndpointSite;
import org.apache.cloudstack.framework.transport.TransportProvider;

public class ClientTransportProvider implements TransportProvider {

	private MessageSerializer _messageSerializer;

	@Override
	public TransportEndpointSite attach(TransportEndpoint endpoint, String predefinedAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean detach(TransportEndpoint endpoint) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public void setMessageSerializer(MessageSerializer messageSerializer) {
		assert(messageSerializer != null);
		_messageSerializer = messageSerializer;
	}

	@Override
	public MessageSerializer getMessageSerializer() {
		return _messageSerializer;
	}
	
	@Override
	public void requestSiteOutput(TransportEndpointSite site) {
		// ???
	}
	
	@Override
	public void sendMessage(String soureEndpointAddress, String targetEndpointAddress, 
		String multiplexier, String message) {
		// TODO
	}
}
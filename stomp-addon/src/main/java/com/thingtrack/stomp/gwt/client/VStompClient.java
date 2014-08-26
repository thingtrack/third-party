/*
 * Copyright 2012 Thingtrack S.L.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.thingtrack.stomp.gwt.client;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VAbsoluteLayout;

public class VStompClient extends VAbsoluteLayout {
    /** Set the CSS class name to allow styling. */
    public static final String CLASSNAME = "v-stomp";

    /** The client side widget identifier */
    private String uidlId;
    private ApplicationConnection client;
    
    private StompClient stompClient;
    
    private ConnectionCallback cbk;
    
    private String host;
    private String username;
    private String password;
    private String destination;
    
    private String topic;
    private String message;
    
    public static final String CMD_INIT = "init";
    public static final String CMD_CONNECT = "connect";
    public static final String CMD_DISCONNECT = "disconnect";
    public static final String CMD_SUBSCRIBE = "subscribe";
    public static final String CMD_UNSUBSCRIBE = "unsubscribe";
    public static final String CMD_SEND = "send";
    
    public static final String ONCONNECT_EVENT = "onConnect";
    public static final String ONDISCONNECT_EVENT = "onDisconnect";
    public static final String ONERROR_EVENT = "onError";
    public static final String ONMESSAGE_EVENT = "onMessage";
    
    public static final String ATTR_COMMAND = "command";
    public static final String ATTR_HOST = "host";
    public static final String ATTR_USERNAME = "username";
    public static final String ATTR_PASSWORD = "password";
    public static final String ATTR_TOPIC = "topic";
    public static final String ATTR_DESTINATION = "destination";
    public static final String ATTR_MESSAGE = "message";
    
    /**
     * The constructor should first call super() to initialize the component and
     * then handle any initialization relevant to Vaadin.
     */
    public VStompClient() {
        super();
        
        setStyleName(CLASSNAME);

    }
    
    @Override
    public void updateFromUIDL(UIDL uidl, final ApplicationConnection client) {
    	if (client.updateComponent(this, uidl, true))
            return;
 
        this.client = client;
        uidlId = uidl.getId();
  		        
        if (uidl.getStringAttribute(ATTR_COMMAND).equals(CMD_INIT)) {
        	host = uidl.getStringAttribute(ATTR_HOST);
            	
           	init();
           	
        } else if (uidl.getStringAttribute(ATTR_COMMAND).equals(CMD_CONNECT)) {
            if (uidl.hasAttribute(ATTR_USERNAME))
               	username = uidl.getStringAttribute(ATTR_USERNAME);
                
            if (uidl.hasAttribute(ATTR_PASSWORD))
               	password = uidl.getStringAttribute(ATTR_PASSWORD);
                
            connect();
            
        } else if (uidl.getStringAttribute(ATTR_COMMAND).equals(CMD_DISCONNECT)) {
        	disconnect();
        	
        } else if (uidl.getStringAttribute(ATTR_COMMAND).equals(CMD_SUBSCRIBE)) {
            if (uidl.hasAttribute(ATTR_TOPIC))
               	topic = uidl.getStringAttribute(ATTR_TOPIC);
                                
            subscribe();
            
        } else if (uidl.getStringAttribute(ATTR_COMMAND).equals(CMD_UNSUBSCRIBE)) {
            if (uidl.hasAttribute(ATTR_TOPIC))
               	topic = uidl.getStringAttribute(ATTR_TOPIC);
            
        	unsubscribe();
        	
        } else if (uidl.getStringAttribute(ATTR_COMMAND).equals(CMD_SEND)) {
        	if (uidl.hasAttribute(ATTR_DESTINATION))
        		destination = uidl.getStringAttribute(ATTR_DESTINATION);
                
                if (uidl.hasAttribute(ATTR_MESSAGE))
                	message = uidl.getStringAttribute(ATTR_MESSAGE);
                
                send(message);
        }
                								
    }
    
    private void init() {
        cbk = new ConnectionCallback() {        	 
			@Override
			public void onError(String message) {
				client.updateVariable(uidlId, ONERROR_EVENT, message, true);
			}
 
			@Override
			public void onDisconnect() {				
				client.updateVariable(uidlId, ONDISCONNECT_EVENT, ONDISCONNECT_EVENT, true);
			}
 
			@Override
			public void onConnect() {
				client.updateVariable(uidlId, ONCONNECT_EVENT, ONCONNECT_EVENT, true);
			}
		};
		
		stompClient = new StompClient("ws://" + host, cbk);
    }
    
    private void connect() {
    	stompClient.connect();
    	    	
    }

    private void disconnect() {
    	stompClient.disconnect();
    	    	
    }
    
    private void subscribe() {
    	final MessageListener listener = new MessageListener() { 
			@Override
			public void onMessage(Message message) {
				client.updateVariable(uidlId, ONMESSAGE_EVENT, message.getBody(), true);
				
			}
		};
		
		if (topic != null)
			stompClient.subscribe(topic, listener);
    }
    
    private void unsubscribe() {
    	if (topic != null)
    		stompClient.unsubscribe(topic);
    	
    }
    
    private void send(String message) {		
		if (destination != null && message != null)
			stompClient.send(destination, message);
		
    }
}

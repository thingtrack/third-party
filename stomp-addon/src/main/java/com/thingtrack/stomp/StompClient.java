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
package com.thingtrack.stomp;

import java.io.Serializable;
import java.util.Map;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.ClientWidget;
import com.thingtrack.stomp.gwt.client.VStompClient;

@SuppressWarnings("serial")
@ClientWidget(VStompClient.class)
public class StompClient extends AbstractComponent {
	private String host;	
	private String username;
	private String password;
	private String destination;	
	private String topic;	
	private String message;
	
	private String command = null;

	private onConnectListener onConnectListener;
	private onDisconnectListener onDisconnectListener;
	private onErrorListener onErrorListener;
	private onMessageListener onMessageListener;
	
	public StompClient(String host) {
		super();
		
		this.host = host;
		this.command = VStompClient.CMD_INIT;
	}
	
    @Override
    public void paintContent(final PaintTarget target) throws PaintException {
    	if (host != null)
    		target.addAttribute(VStompClient.ATTR_HOST, host);
    	
    	if (username != null)
        	target.addAttribute(VStompClient.ATTR_USERNAME, username);

    	if (password != null)
        	target.addAttribute(VStompClient.ATTR_PASSWORD, password);
    	
    	if (destination != null)
        	target.addAttribute(VStompClient.ATTR_DESTINATION, destination);
    	        
        if (topic != null)
        	target.addAttribute(VStompClient.ATTR_TOPIC, topic);

        if(message != null) {
        	target.addAttribute(VStompClient.ATTR_MESSAGE, message);
        	message = null;
        }
        
        if(command != null) {
        	target.addAttribute(VStompClient.ATTR_COMMAND, command);
        	
        }
        
        super.paintContent(target);
        
    }
    

    @SuppressWarnings("unchecked")
	@Override
    public void changeVariables(final Object source, final Map variables) {
        super.changeVariables(source, variables);
        
        String result = null;        
        if (variables.containsKey(VStompClient.ONCONNECT_EVENT)) {
        	result = (String) variables.get(VStompClient.ONCONNECT_EVENT);
        	
            if (onConnectListener != null)
            	onConnectListener.onConnect(result);
        }
        else if (variables.containsKey(VStompClient.ONDISCONNECT_EVENT)) {
        	result = (String) variables.get(VStompClient.ONDISCONNECT_EVENT);
        	
            if (onDisconnectListener != null)
            	onDisconnectListener.onDisconnect(result);
        	
        }
        else if (variables.containsKey(VStompClient.ONMESSAGE_EVENT)) {
        	result = (String) variables.get(VStompClient.ONMESSAGE_EVENT);
        	
            if (onMessageListener != null)
            	onMessageListener.onMessage(result);
        	
        }
        else if (variables.containsKey(VStompClient.ONERROR_EVENT)) {
        	result = (String) variables.get(VStompClient.ONERROR_EVENT);

            if (onErrorListener != null)
            	onErrorListener.onError(result);
        }
    }
    
    public interface onConnectListener extends Serializable {
        public void onConnect(String message);
        
    }
    
    public interface onDisconnectListener extends Serializable {
        public void onDisconnect(String message);
        
    }
     
    public interface onErrorListener extends Serializable {
        public void onError(String message);
        
    }
    
    public interface onMessageListener extends Serializable {
        public void onMessage(String message);
        
    }
    
    public void addOnConnectListener(onConnectListener onConnectListener) {
    	this.onConnectListener = onConnectListener;
    	
    }
    
    public void addOnDisconnectListener(onDisconnectListener onDisconnectListener) {
    	this.onDisconnectListener = onDisconnectListener;
    	
    }
    
    public void addOnErrorListener(onErrorListener onErrorListener) {
    	this.onErrorListener = onErrorListener;
    	
    }
    
    public void addOnMessageListener(onMessageListener onMessageListener) {
    	this.onMessageListener = onMessageListener;
    	
    }
    
    public void connect(String username, String password) {
    	this.username = username;
    	this.password = password;    	
    	this.command = VStompClient.CMD_CONNECT;
    	
    	requestRepaint();
    }
    
    public void disconnect() {    	
    	this.command = VStompClient.CMD_DISCONNECT; 
    	
    	requestRepaint();
    }
        
    public void subscribe(String topic) {
    	this.topic = topic;    	
    	this.command = VStompClient.CMD_SUBSCRIBE;
    	
    	requestRepaint();
    }
    
    public void unsubscribe(String topic) {
    	this.topic = topic;
    	this.command = VStompClient.CMD_UNSUBSCRIBE;
    	
    	requestRepaint();
    }
    
    public void send(String message, String destination) {
    	this.destination = destination;
    	this.message = message;
    	this.command = VStompClient.CMD_SEND;
    	
    	requestRepaint();
    }
        
	public String getHost() {
		return this.host;
		
	}

	public String getUsername() {
		return this.username;
		
	}

	public String getPassword() {
		return this.password;
		
	}

	public String getDestination() {
		return this.destination;
		
	}

	public String getSubscription() {
		return this.topic;
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setSubscription(String subscription) {
		this.topic = subscription;
	}

}

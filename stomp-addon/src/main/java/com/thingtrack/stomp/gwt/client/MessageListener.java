package com.thingtrack.stomp.gwt.client;

/**
 * 
 * @author Vinicius Carvalho
 * Marker interface that receives messages from the broker
 */
public interface MessageListener {
	public void onMessage(Message message);
}

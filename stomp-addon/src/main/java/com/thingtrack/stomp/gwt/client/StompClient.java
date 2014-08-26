package com.thingtrack.stomp.gwt.client;
/**
 * 
 * @author Vinicius Carvalho
 * Wraps the Stomp JS client.
 * 
 * This class connects to a remote broker and maintains a list of subscriptions.
 * Each subscription is associated if a MessageListener. Only one instance of this class
 * should exist per server, and subscriptions to different channels (Queue/Topic) should be
 * created as required.
 * 
 */
public class StompClient {
	
	String url;
	ConnectionCallback callback;
	
	public StompClient(String url){
		this(url,null);
	}
	
	public StompClient(String url, ConnectionCallback callback){
		this.url = url;
		this.callback = callback;
		init();
	}
	
	private native final void init()/*-{
		$wnd.subscriptions = new Array();
		$wnd.stompClient = $wnd.Stomp.client(this.@com.thingtrack.stomp.gwt.client.StompClient::url);
	}-*/;
	
	
	/**
	 * Connects to the JMS broker and invokes the callback interface if one was provided
	 */
	public native final void connect() /*-{
		var that = this;
		var onsuccess = function(){
			that.@com.thingtrack.stomp.gwt.client.StompClient::onConnect()();
		}
		var onfail = function(cause){
			that.@com.thingtrack.stomp.gwt.client.StompClient::onError(Ljava/lang/String;)(cause);
		}
		$wnd.stompClient.connect('guest','guest',onsuccess,onfail);
	}-*/;
	
	/**
	 * Disconnects from the server and removes any subscriptions that are still active
	 */
	public native final void disconnect() /*-{
		var that = this;
		if($wnd.subscriptions.length > 0){
			for(var i=0;i<$wnd.subscriptions.length;i++){
				$wnd.stompClient.unsubscribe($wnd.subscriptions[i]);
			}
		}
		var ondisconnect = function(){
			that.@com.thingtrack.stomp.gwt.client.StompClient::onDisconnect()();
		}
		$wnd.stompClient.disconnect(ondisconnect);
}-*/;
	/**
	 * Subscribes the given listener to a certain destination. An identifier from the subscription is returned
	 * that id should be used to unsubscribe from the channel
	 * @param channel - The name of the Queue/Topic
	 * @param listener - Implementation of your message Listener
	 * @return Subscription Identifier
	 */
	public native final String subscribe(String channel, MessageListener listener)/*-{
		var onmessage = function(message){
			listener.@com.thingtrack.stomp.gwt.client.MessageListener::onMessage(Lcom/thingtrack/stomp/gwt/client/Message;)(message);
		}
		var id = $wnd.stompClient.subscribe(channel,onmessage);
		$wnd.subscriptions.push(id);
		return id;
	}-*/;	
	/**
	 * Unsubscribe from the channel.
	 * @param subscriptionId The id of the subscription to be unsubscribed
	 */
	public native final void unsubscribe(String subscriptionId)/*-{
		var idx = $wnd.subscriptions.indexOf(subscriptionId);
		$wnd.subscriptions.splice(idx,1);
		$wnd.stompClient.unsubscribe(subscriptionId);
	}-*/;
	/**
	 * Sends a message with the headers to a destination
	 * @param destination - The id of the Channel
	 * @param message - The Message with the headers
	 */
	public native final void send(String destination, Message message)/*-{
		$wnd.stompClient.send(destination, message.headers, message.body);
	}-*/;
	/**
	 * Sends a text to a destination.
	 * @param destination - The id of the Channel
	 * @param body - The message contents as a String
	 */
	public native final void send(String destination, String body) /*-{
		$wnd.stompClient.send(destination,{},body);
	}-*/;
	
	void onConnect(){
		if(callback != null){
			callback.onConnect();
		}
	}
	
	void onError(String cause){
		if(callback != null){
			callback.onError(cause);
		}
	}
	
	void onDisconnect(){
		if(callback != null){
			callback.onDisconnect();
		}
	}
	
}


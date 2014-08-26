package com.thingtrack.stomp;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class StompTestApplication extends Application
{
    private Window window;
    private StompClient stompClient;
    
    @Override
    public void init()
    {
        window = new Window("Stomp Test");
        setMainWindow(window);
        
        Button button = new Button("Connect");
        button.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	try {
            		stompClient.connect("admin", "password");
            	}
            	catch(Exception ex) {
            		ex.getMessage();
            	}
    	        
            }
        });
        window.addComponent(button);
        
        Button message = new Button("Send Message");
        message.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	try {
            		stompClient.send("Hello Stomp!", "/topic/chat.general");
            	}
            	catch(Exception ex) {
            		ex.getMessage();
            	}
    	        
            }
        });
        window.addComponent(message);        
              
        try {	        
	        stompClient = new StompClient("localhost:61614");	        
	        window.addComponent(stompClient);
	        
        }
        catch(Exception ex) {
        	ex.getMessage();
        }
    }
    
}

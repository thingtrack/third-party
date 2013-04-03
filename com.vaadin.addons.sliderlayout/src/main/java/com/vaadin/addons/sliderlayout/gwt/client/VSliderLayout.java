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
package com.vaadin.addons.sliderlayout.gwt.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Effects;
import gwtquery.plugins.effects.easing.client.EasingExt;

import java.util.Iterator;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VAbsoluteLayout;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.css.CSS;
import com.google.gwt.query.client.plugins.effects.PropertiesAnimation.Easing;

public class VSliderLayout extends VAbsoluteLayout {
    /** Set the CSS class name to allow styling. */
    public static final String CLASSNAME = "v-sliderlayout";
    
    public static final String SLIDE_DEFAULT_CLASS_NAME = ".v-slide";

    /** The client side widget identifier */
    private String uidlId;
    
    // initialize slide name
    String lastSlideName = "v-slide-0";
    
    /**
     * The constructor should first call super() to initialize the component and
     * then handle any initialization relevant to Vaadin.
     */
    public VSliderLayout() {
        super();
        
        setStyleName(CLASSNAME);
    }
    
    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
    	super.updateFromUIDL(uidl, client);
    	        
        // Save the client side identifier (paintable id) for the widget
        uidlId = uidl.getId();
        
        // initialize slide index
        int index = 0;
        
        // initialize zIndex slide number
        int slideZindex = uidl.getChildCount();
        
        // repocolate all slides using margin left attribute and zIndex for each slide added to the slider (Absolute layout)
        for (Iterator<Object> childIterator = uidl.getChildIterator(); childIterator.hasNext();) {
        	UIDL cc = (UIDL) childIterator.next();
        	if (cc.getTag().equals("cc")) {
        		UIDL componentUIDL = cc.getChildUIDL(0);        	        		
        			
        		Paintable componentPaintable = client.getPaintable(componentUIDL);        		
        		
        		Element componentElement = null;
				if (componentPaintable != null) {										
					String pid = client.getPid(componentPaintable);
															
					componentElement = client.getElementByPid(pid);
															
					if (componentElement.getClassName().contains("v-customcomponent")) {
		        		if (index == 0)
		        			componentElement.getParentElement().getStyle().setZIndex(1);
		        		
						// set margin left to horizontal stack slides
						componentElement.getStyle().setMarginLeft(100 * index, Unit.PCT);
						
						// set z-index in descendant order for each slide
						componentElement.getStyle().setZIndex(slideZindex);
						
						// set particulat slide class name to all slides of this slider
						componentElement.addClassName("v-slide-" + uidlId);
						
						slideZindex = slideZindex - 1;
						index = index + 1;
					}
						
				}
					
        	}
        }
        
    	//super.updateFromUIDL(uidl, client);      
                
    	// check if i must to move
        if (uidl.hasAttribute("slideTo")) {
        	rollTo(uidlId, uidl.getIntAttribute("slideTo"), uidl.getStringAttribute("animation"), uidl.getIntAttribute("duration"));
				
		}
    
    }
     
	//private void rollTo(String id, final int slideTo) {
	private void rollTo(String id, final int slideTo, String animation, int duration) {
		GQuery panel = $(SLIDE_DEFAULT_CLASS_NAME + "-" + uidlId);
		
		if (panel == null)
			return;
		
		if (panel.widgets().size() == 0)
			panel.css(CSS.LEFT, "0");

		// animate slide to number
		panel.as(Effects)
			//.delay(1000)
			//.animate("left: -" + Integer.toString(100 * slideTo) + "%", 1000, EasingExt.EASE_OUT_BOUNCE, new Function() {
			// don't use 0% (only compatible with webkit browsers) for left property use 0px to be compatible with firefox and IE and opera !!!
			  .animate("left: -" + Integer.toString(panel.outerWidth() * slideTo), duration, getEasing(animation), new Function() {
				boolean fistEvent = true;
			
				public void f(Element e){
					// only send the first event from all slides movement 
					if (fistEvent) {
						client.updateVariable(uidlId, "fromSlideName", lastSlideName, false);
						client.updateVariable(uidlId, "toSlideName", "v-slide-" + slideTo, true);
            		
						// save the last Slide name
						lastSlideName = "v-slide-" + slideTo;
            		
						fistEvent = false;
	            	}
	            }
	            
	        });
				
	}
	
	private Easing getEasing(String animation) {
		if (animation.equals("LINEAR"))
			return EasingExt.LINEAR;
		
		if (animation.equals("SWING"))
			return EasingExt.SWING;
		
		if (animation.equals("EASE_IN_QUAD"))
			return EasingExt.EASE_IN_QUAD;
		
		if (animation.equals("EASE_OUT_QUAD"))
			return EasingExt.EASE_OUT_QUAD;
		
		if (animation.equals("EASE_IN_OUT_QUAD"))
			return EasingExt.EASE_IN_OUT_QUAD;
		
		if (animation.equals("EASE_IN_CUBIC"))
			return EasingExt.EASE_IN_CUBIC;
		
		if (animation.equals("EASE_OUT_CUBIC"))
			return EasingExt.EASE_OUT_CUBIC;
		
		if (animation.equals("EASE_IN_OUT_CUBIC"))
			return EasingExt.EASE_IN_OUT_CUBIC;
		
		if (animation.equals("EASE_IN_QUART"))
			return EasingExt.EASE_IN_QUART;
		
		if (animation.equals("EASE_OUT_QUART"))
			return EasingExt.EASE_OUT_QUART;
		
		if (animation.equals("EASE_IN_OUT_QUART"))
			return EasingExt.EASE_IN_OUT_QUART;
		
		if (animation.equals("EASE_IN_QUINT"))
			return EasingExt.EASE_IN_QUINT;
		
		if (animation.equals("EASE_OUT_QUINT"))
			return EasingExt.EASE_OUT_QUINT;
		
		if (animation.equals("EASE_IN_OUT_QUINT"))
			return EasingExt.EASE_IN_OUT_QUINT;
		
		if (animation.equals("EASE_IN_SINE"))
			return EasingExt.EASE_IN_SINE;
		
		if (animation.equals("EASE_OUT_SINE"))
			return EasingExt.EASE_OUT_SINE;
		
		if (animation.equals("EASE_IN_OUT_SINE"))
			return EasingExt.EASE_IN_OUT_SINE;
		
		if (animation.equals("EASE_IN_EXPO"))
			return EasingExt.EASE_IN_EXPO;
		
		if (animation.equals("EASE_OUT_EXPO"))
			return EasingExt.EASE_OUT_EXPO;
		
		if (animation.equals("EASE_IN_OUT_EXPO"))
			return EasingExt.EASE_IN_OUT_EXPO;
				
		if (animation.equals("EASE_IN_CIRC"))
			return EasingExt.EASE_IN_CIRC;
		
		if (animation.equals("EASE_OUT_CIRC"))
			return EasingExt.EASE_OUT_CIRC;
		
		if (animation.equals("EASE_IN_OUT_CIRC"))
			return EasingExt.EASE_IN_OUT_CIRC;
		
		if (animation.equals("EASE_IN_ELASTIC"))
			return EasingExt.EASE_IN_ELASTIC;
				
		if (animation.equals("EASE_OUT_ELASTIC"))
			return EasingExt.EASE_OUT_ELASTIC;
		
		if (animation.equals("EASE_IN_OUT_ELASTIC"))
			return EasingExt.EASE_IN_OUT_ELASTIC;
		
		if (animation.equals("EASE_IN_BACK"))
			return EasingExt.EASE_IN_BACK;
		
		if (animation.equals("EASE_OUT_BACK"))
			return EasingExt.EASE_OUT_BACK;
		
		if (animation.equals("EASE_IN_OUT_BACK"))
			return EasingExt.EASE_IN_OUT_BACK;
		
		if (animation.equals("EASE_IN_BOUNCE"))
			return EasingExt.EASE_IN_BOUNCE;
		
		if (animation.equals("EASE_OUT_BOUNCE"))
			return EasingExt.EASE_OUT_BOUNCE;
		
		if (animation.equals("EASE_IN_OUT_BOUNCE"))
			return EasingExt.EASE_IN_OUT_BOUNCE;
		
		return EasingExt.LINEAR;
	}
}

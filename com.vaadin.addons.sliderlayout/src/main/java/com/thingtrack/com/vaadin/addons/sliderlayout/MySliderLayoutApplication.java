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
package com.thingtrack.com.vaadin.addons.sliderlayout;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import com.thingtrack.com.vaadin.addons.sliderlayout.SliderLayout.AnimationTerminateListener;
import com.thingtrack.com.vaadin.addons.sliderlayout.layout.Detail;
import com.thingtrack.com.vaadin.addons.sliderlayout.layout.Master;
import com.thingtrack.com.vaadin.addons.sliderlayout.layout.SubDetail01;
import com.thingtrack.com.vaadin.addons.sliderlayout.layout.SubDetail02;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class MySliderLayoutApplication extends Application
{
    private Window window;
    private VerticalLayout mainLayout;

	private HorizontalLayout toolBar;
	private TabSheet tabsheet;
    private SliderLayout sliderLayout_01;
    private SliderLayout sliderLayout_02;
    private SliderLayout sliderLayout_03;
    
    private ANIMATION animation = ANIMATION.LINEAR;
    private int duration = 1000;
    
    @Override
    public void init()
    {
    	window = new Window("My SlideLayout Application Demo");     
        setMainWindow(window);
        
        mainLayout = (VerticalLayout)window.getContent();
        mainLayout.setSizeFull();
        mainLayout.setMargin(false); 
        
        toolBar = new HorizontalLayout();

        // list animation list
        final ComboBox comboboxAnimation = new ComboBox();
        comboboxAnimation.setWidth("250px");
        
        comboboxAnimation.setNullSelectionAllowed(false);
                     
        comboboxAnimation.addItem(ANIMATION.LINEAR);
        comboboxAnimation.addItem(ANIMATION.SWING);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_QUAD);
        comboboxAnimation.addItem(ANIMATION.EASE_OUT_QUAD);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_OUT_QUAD);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_CUBIC);
        comboboxAnimation.addItem(ANIMATION.EASE_OUT_CUBIC);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_OUT_CUBIC);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_QUART);
        comboboxAnimation.addItem(ANIMATION.EASE_OUT_QUART);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_OUT_QUART);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_QUINT);
        comboboxAnimation.addItem(ANIMATION.EASE_OUT_QUINT);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_OUT_QUINT);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_SINE);
        comboboxAnimation.addItem(ANIMATION.EASE_OUT_SINE);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_OUT_SINE);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_EXPO);
        comboboxAnimation.addItem(ANIMATION.EASE_OUT_EXPO);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_OUT_EXPO);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_CIRC);
        comboboxAnimation.addItem(ANIMATION.EASE_OUT_CIRC);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_OUT_CIRC);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_ELASTIC);
        comboboxAnimation.addItem(ANIMATION.EASE_OUT_ELASTIC);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_OUT_ELASTIC);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_BACK);
        comboboxAnimation.addItem(ANIMATION.EASE_OUT_BACK);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_BOUNCE);
        comboboxAnimation.addItem(ANIMATION.EASE_OUT_BOUNCE);
        comboboxAnimation.addItem(ANIMATION.EASE_IN_OUT_BOUNCE);
        
        // Preselect an animation linear
        comboboxAnimation.setValue(ANIMATION.LINEAR);
        
        comboboxAnimation.setDescription("Visit <a href=\"http://gwtquery-easingext.googlecode.com/git/demo/demo.html\">gwtquery-easing</a> animation plugin acceleration curves.");
        comboboxAnimation.setInvalidAllowed(false);
        comboboxAnimation.setNullSelectionAllowed(false);
        comboboxAnimation.setImmediate(true);
        
        comboboxAnimation.addListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				animation = (ANIMATION) comboboxAnimation.getValue();
				
			}
        });
        
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(new Label("&nbsp&nbsp<b>Animation:</b>&nbsp&nbsp", Label.CONTENT_XHTML));
        horizontalLayout.addComponent(comboboxAnimation);
        horizontalLayout.setExpandRatio(comboboxAnimation, 1.0f);
        toolBar.addComponent(horizontalLayout);
        
        // duration value        
        final ObjectProperty<Integer> value = new ObjectProperty<Integer>(1000);
        final TextField positionText = new TextField(value);
        
        Property.ValueChangeListener listener =
            new Property.ValueChangeListener() {
                public void valueChange(ValueChangeEvent event) {
                	duration = value.getValue();
                }
        };
        positionText.addListener(listener);
        positionText.setImmediate(true);
        
        HorizontalLayout horizontalLayoutPosition = new HorizontalLayout();
        horizontalLayoutPosition.addComponent(new Label("&nbsp&nbsp<b>Duration:</b>&nbsp&nbsp", Label.CONTENT_XHTML));
        horizontalLayoutPosition.addComponent(positionText);
        horizontalLayoutPosition.setExpandRatio(positionText, 1.0f);
        toolBar.addComponent(horizontalLayoutPosition);
        
        // roll Previous Button
        Button buttonPrevious = new Button("Roll Previous");
        buttonPrevious.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {                   	
            	switch (tabsheet.getTabPosition(tabsheet.getTab(tabsheet.getSelectedTab()))) {
                	case 0:  sliderLayout_01.rollPrevious(duration, animation);
                		break;
                	case 1:  sliderLayout_02.rollPrevious(duration, animation);
                    	break;
                	case 2:  sliderLayout_03.rollPrevious(duration, animation);
                		break;                    	
            	}
            	            	            	
            }
        });
        toolBar.addComponent(buttonPrevious);
        
        // roll Next Button
        Button buttonNext = new Button("Roll Next");
        buttonNext.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	switch (tabsheet.getTabPosition(tabsheet.getTab(tabsheet.getSelectedTab()))) {
	            	case 0:  sliderLayout_01.rollNext(duration, animation);
	            		break;
	            	case 1:  sliderLayout_02.rollNext(duration, animation);
	                	break;
	            	case 2:  sliderLayout_03.rollNext(duration, animation);
	            		break;                    	
            	}
            	
            }
        });
        toolBar.addComponent(buttonNext);
               
        final TextField slideNum = new TextField();
        slideNum.setValue(0);
        toolBar.addComponent(slideNum);
        
        // roll To Button
        Button buttonRoll = new Button("Roll To");
        buttonRoll.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	Object value = slideNum.getValue();
            	
            	switch (tabsheet.getTabPosition(tabsheet.getTab(tabsheet.getSelectedTab()))) {
	            	case 0:  sliderLayout_01.rollTo(Integer.parseInt(value.toString()), duration, animation);
	            		break;
	            	case 1:  sliderLayout_02.rollTo(Integer.parseInt(value.toString()), duration, animation);
	                	break;
	            	case 2:  sliderLayout_03.rollTo(Integer.parseInt(value.toString()), duration, animation);
	            		break;                    	
            	}
            
            }
        });
        toolBar.addComponent(buttonRoll);
        
        // add new slide
        Button buttonSubDetail= new Button("New Slide");
        buttonSubDetail.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {            	
                SubDetail01 subDetail = new SubDetail01();
                
            	switch (tabsheet.getTabPosition(tabsheet.getTab(tabsheet.getSelectedTab()))) {
	            	case 0:  sliderLayout_01.addSlide(subDetail);
	            		break;
	            	case 1:  sliderLayout_02.addSlide(subDetail);
	                	break;
	            	case 2:  sliderLayout_03.addSlide(subDetail);
	            		break;                    	
            	}
            	                
            }
        });
        toolBar.addComponent(buttonSubDetail);
        
        mainLayout.addComponent(toolBar);
        mainLayout.setExpandRatio(toolBar, 0.0f);
                
        tabsheet = new TabSheet();
        tabsheet.setSizeFull();
        
        // create the Slider Layout and configure the SlideLayout behavior from tab 01
        // EXAMPLE 01: this example add the slides but don't roll to the next slide and don't remove when go forward
        sliderLayout_01 = new SliderLayout();
        sliderLayout_01.setRemoveSlide(false);
        
        sliderLayout_01.addListener(new AnimationTerminateListener() {			
			@Override
			public void slideTerminated(Component fromSlideName, Component toSlideName) {
				if (fromSlideName != null && toSlideName != null)
					window.showNotification("From: " + fromSlideName.getClass().getName() + "-> To: " + toSlideName.getClass().getName(), Notification.TYPE_HUMANIZED_MESSAGE);
				
			}
		});
        
        sliderLayout_01.setSizeFull();
                
        Master master = new Master();
        sliderLayout_01.addSlide(master, false); // don't roll to the slide added
        
        Detail detail = new Detail();
        sliderLayout_01.addSlide(detail, false); // don't roll to the slide added

        VerticalLayout verticalLayout_01 = new VerticalLayout();
        verticalLayout_01.setSizeFull();
                 
        verticalLayout_01.addComponent(new Label("<label style=\"background-color:#66CC66; display:block; \"><b>EXAMPLE 01:</b> this example add the slides but don't roll to the next slide and don't remove when go forward</label>", Label.CONTENT_XHTML));
        verticalLayout_01.addComponent(sliderLayout_01);
        verticalLayout_01.setExpandRatio(sliderLayout_01, 1.0f);
        
        tabsheet.addTab(verticalLayout_01, "EXAMPLE 01");
        
        // create the Slider Layout and configure the SlideLayout behavior from tab 02
        // EXAMPLE 02: this example add the slides and roll to the next slide when is added and don't remove when go forward
        sliderLayout_02 = new SliderLayout();
        sliderLayout_02.setRemoveSlide(false);
        
        sliderLayout_02.addListener(new AnimationTerminateListener() {			
			@Override
			public void slideTerminated(Component fromSlideName, Component toSlideName) {
				if (fromSlideName != null && toSlideName != null)
					window.showNotification("From: " + fromSlideName.getClass().getName() + "-> To: " + toSlideName.getClass().getName(), Notification.TYPE_HUMANIZED_MESSAGE);
				
			}
		});
        
        sliderLayout_02.setSizeFull();
                
        SubDetail01 subDetail01 = new SubDetail01(); 
        sliderLayout_02.addSlide(subDetail01);

        SubDetail02 subDetail02 = new SubDetail02(); 
        sliderLayout_02.addSlide(subDetail02);
        
        VerticalLayout verticalLayout_02 = new VerticalLayout();
        verticalLayout_02.setSizeFull();
               
        verticalLayout_02.addComponent(new Label("<label style=\"background-color:#66CC66; display:block; \"><b>EXAMPLE 02:</b> this example add the slides and roll to the next slide when is added and don't remove when go forward</label>", Label.CONTENT_XHTML));        
        verticalLayout_02.addComponent(sliderLayout_02);
        verticalLayout_02.setExpandRatio(sliderLayout_02, 1.0f);
        
        tabsheet.addTab(verticalLayout_02, "EXAMPLE 02");
        
        // create the Slider Layout and configure the SlideLayout behavior from tab 03
        // EXAMPLE 03: this example add the slides and roll to the next slide when is added and remove when go forward
        sliderLayout_03 = new SliderLayout();
        sliderLayout_03.setRemoveSlide(true);
        
        sliderLayout_03.addListener(new AnimationTerminateListener() {			
			@Override
			public void slideTerminated(Component fromSlideName, Component toSlideName) {
				if (fromSlideName != null && toSlideName != null)
					window.showNotification("From: " + fromSlideName.getClass().getName() + "-> To: " + toSlideName.getClass().getName(), Notification.TYPE_HUMANIZED_MESSAGE);
				
			}
		});
        
        sliderLayout_03.setSizeFull();
        
        SubDetail01 subDetail013 = new SubDetail01(); 
        sliderLayout_03.addSlide(subDetail013);

        SubDetail02 subDetail023 = new SubDetail02(); 
        sliderLayout_03.addSlide(subDetail023);
               
        VerticalLayout verticalLayout_03 = new VerticalLayout();
        verticalLayout_03.setSizeFull();
            
        verticalLayout_03.addComponent(new Label("<label style=\"background-color:#66CC66; display:block; \"><b>EXAMPLE 03:</b> this example add the slides and roll to the next slide when is added and remove when go forward</label>", Label.CONTENT_XHTML));
        verticalLayout_03.addComponent(sliderLayout_03);
        verticalLayout_03.setExpandRatio(sliderLayout_03, 1.0f);
        
        tabsheet.addTab(verticalLayout_03, "EXAMPLE 03");                
        
        // add tabsheet to main layout
        mainLayout.addComponent(tabsheet);
        mainLayout.setExpandRatio(tabsheet, 1.0f);
        
    }
    
}

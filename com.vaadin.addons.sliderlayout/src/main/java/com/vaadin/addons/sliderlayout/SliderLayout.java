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
package com.vaadin.addons.sliderlayout;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import com.vaadin.addons.sliderlayout.gwt.client.VSliderLayout;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.Component;


@SuppressWarnings("serial")
@ClientWidget(VSliderLayout.class)
public class SliderLayout extends AbsoluteLayout {	
	private static final String SLIDE_CLASS_NAME_PREFIX = "v-slide-";
	
	private static final int DEFAULT_SLIDE_NUMBER = -1;
	private static final ANIMATION DEFAULT_ANIMATION = ANIMATION.LINEAR;
	private static final int DEFAULT_DURATION = 1000; // in miliseconds
	
	private Hashtable<String, Component> slides = new Hashtable<String, Component>();
	
	private AnimationTerminateListener listener;
	
	private String lastSlideName = "v-slide-0";

	private boolean removeSlide = true;	
	private int slideTo = DEFAULT_SLIDE_NUMBER;
	private ANIMATION animation = DEFAULT_ANIMATION;
	private int duration = DEFAULT_DURATION; 
	
	public SliderLayout() {
		super();
		
	}
	
	/**
	 * Return the SlideLayout behavior flag
	 * @return SlideLayout behavior flag
	 */
	public boolean isRemoveSlide() {
		return removeSlide;
	}

	/**
	 * Configure slider layout animation type
	 * @param  slider animation type
	 */
	public void setAnimation(ANIMATION animation) {
		this.animation = animation;
		
	}
	
	/** 
	 * Return the SlideLayout animation type
	 * @return SlideLayout animation type
	 */
	public ANIMATION getAnimation() {
		return animation;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}
	
	/**
	 * Configure the SlideLayout behavior when navigate.
	 * If we can use the component to navigate in a linear way, we must set true
	 * this flag to remove the slides because we can have a tree slides relation.
	 * If we can make a wizard style app, we must set the param to false to don't 
	 * remove the prevoius slides and couls return again to that.
	 * @param  removeSlide  flag to indicate if we want remove slide
	 */
	public void setRemoveSlide(boolean removeSlide) {
		this.removeSlide = removeSlide;
		
	}
	
    @Override
    public void paintContent(final PaintTarget target) throws PaintException {                
        if(slideTo != DEFAULT_SLIDE_NUMBER) {
        	target.addAttribute("slideTo", slideTo);
        	slideTo = DEFAULT_SLIDE_NUMBER;
        }
        
        target.addAttribute("animation", animation.name());
        target.addAttribute("duration", duration);

        super.paintContent(target);
        
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void changeVariables(final Object source, final Map variables) {
        super.changeVariables(source, variables);
     
        // get from and to slides animated
        String fromSlideName = null;        
        if (variables.containsKey("fromSlideName")) 
        	fromSlideName = (String) variables.get("fromSlideName");
        
        String toSlideName = null;
        if (variables.containsKey("toSlideName"))
            toSlideName = (String) variables.get("toSlideName");
        
        // remove slides if is necesary       
        if (removeSlide && getSlideFromName(fromSlideName) > getSlideFromName(toSlideName))
        {
			removeComponent(slides.get(lastSlideName));
			
			slides.remove(lastSlideName);
        }
        	
        if (listener != null)
        	listener.slideTerminated(slides.get(fromSlideName), slides.get(toSlideName));
        
        // save last slide animated
        this.lastSlideName = toSlideName;
        
    }
    
	/**
	 * Add a new slide to be controlled with the SlideLayout. For default
	 * the slide will be roll after is created
	 * @param  slide any Vaadin Component that we want roll with the SlideLayout
	 */
	public void addSlide(Component slide) {
		// add new slide without animation
		addSlide(slide, true);
	}
	
	/**
	 * Add a new slide to be controlled with the SlideLayout controlled with a roll
	 * flag animation.
	 * @param  slide any Vaadin Component that we want roll with the SlideLayout
	 * @param  animateToNext flag to control if we want to roll or not the new slide
	 */
	public void addSlide(Component slide, boolean animateToNext) {
		// create new slide
		createSlide(slide);
		
		// initialize slide number to be animated
		if (animateToNext)			
			this.slideTo = getSlideFromName(lastSlideName) + 1;			
		
	}
	
	/**
	 * Add a new slide to be controlled with the SlideLayout controlled and roll
	 * to a particular position if exist any slide.
	 * @param  slide any Vaadin Component that we want roll with the SlideLayout
	 * @param  slideTo slide number to navigate
	 */
	public void addSlide(Component slide, int slideTo) {
		// create new slide
		createSlide(slide);
		
		// initialize slide number to be animated
		this.slideTo = slideTo;
		
	}
	
	/**
	 * Roll to the next slide if exist one with default linear animation
	 */
	public void rollNext() {
		rollNext(DEFAULT_DURATION, DEFAULT_ANIMATION);

	}
	
	/**
	 * Roll to the next slide if exist one.
	 */
	public void rollNext(int duration, ANIMATION animation) {		
		// get next slide unique name
		int slideTo = getSlideFromName(lastSlideName) + 1;	
		
		// return if not exit the slide
		if (slides.get(getSlideFromNumber(slideTo)) == null)
			return;
		
		// return if the next slide is the same
		if (lastSlideName.equals(getSlideFromNumber(slideTo)))
			return;
		
		// initialize animation
		this.duration = duration;
		
		// initialize animation
		this.animation = animation;
		
		// initialize slide id
		this.slideTo = slideTo;
		
		requestRepaint();

	}
	
	/**
	 * Roll to the previous slide if exist one. The flag removeSlide
	 * control if the upper slide is remove or not when the roll finish
	 * event is fired with default linear animation
	 */
	public void rollPrevious() {
		rollPrevious(DEFAULT_DURATION, DEFAULT_ANIMATION);
		
	}
	
	/**
	 * Roll to the previous slide if exist one. The flag removeSlide
	 * control if the upper slide is remove or not when the roll finish
	 * event is fired 
	 */
	public void rollPrevious(int duration, ANIMATION animation) {		
		// get previous slide unique name
		int slideTo = getSlideFromName(lastSlideName) - 1;	
		
		// return if not exit the slide
		if (slides.get(getSlideFromNumber(slideTo)) == null)
			return;
		
		// return if the previous slide is the same
		if (lastSlideName.equals(getSlideFromNumber(slideTo)))
			return;
		
		// initialize duration
		this.duration = duration;
		
		// initialize animation
		this.animation = animation;
		
		// initialize slide id
		this.slideTo = slideTo;
				
		requestRepaint();

	}
	
	/**
	 * Roll to a particular slide number if ecxist one with default linear animation
	 * @param  slideTo slide number to navigate
	 */
	public void rollTo(int slideTo) {
		rollTo(slideTo, DEFAULT_DURATION, DEFAULT_ANIMATION);

	}
	
	/**
	 * Roll to a particular slide number if exist one
	 * @param  slideTo slide number to navigate
	 * @param  animation slide animation
	 */
	public void rollTo(int slideTo, int duration, ANIMATION animation) {		
		// return if not exit the slide
		if (slides.get(getSlideFromNumber(slideTo)) == null)
			return;
		
		// return if the slide is the same
		if (lastSlideName.equals(getSlideFromNumber(slideTo)))
			return;
		
		// initialize duration
		this.duration = duration;
		
		// initialize animation
		this.animation = animation;
		
		// initialize slide id
		this.slideTo = slideTo;
		
		requestRepaint();

	}
	
	/**
	 * Initialize the new slide created and set the general class and particular classes
	 * for everyone
	 * @param  slide any Vaadin Component that we want roll with the SlideLayout
	 */
	private void createSlide(Component slide) {
		// maximize size component
		slide.setSizeFull();
		
		// set default slide style name: v-slide
		// set particular slide style name: v-slide-<n>	
		String slideParticularClassName = SLIDE_CLASS_NAME_PREFIX + Integer.toString(slides.size());
		
		// add component to the layout
		addComponent(slide, "top: 0px; left: 0px;");
		
		// register new component in layout hashtable
		slides.put(slideParticularClassName, slide);
		
	}
	
	/**
	 * Get the unique slide class name from its number id
	 * @param  identifacion slide number
	 * @return unique class slide name
	 */
	private String getSlideFromNumber(int id) {
		return SLIDE_CLASS_NAME_PREFIX + id;
		
	}

	/**
	 * Get the id slide number from its unique slide class name
	 * @param  id unique slide class name
	 * @return identification slide number
	 */
	private int getSlideFromName(String id) {
		return Integer.parseInt(id.replace(SLIDE_CLASS_NAME_PREFIX, ""));
		
	}
	    
	/**
	 * SlideLayout finish animating listener to control the from and to
	 * animated slides
	 * @param  id unique slide class name
	 * @return identification slide number
	 */
    public interface AnimationTerminateListener extends Serializable {
        public void slideTerminated(Component fromSlideName, Component toSlideName);
        
    }
    
    public void addListener(AnimationTerminateListener listener) {
    	this.listener = listener;
    	
    }

}

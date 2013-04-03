package com.vaadin.addons.sliderlayout;

/**
 * Easing animation document
 * @link http://gwtquery-easingext.googlecode.com/git/demo/demo.html
 */
public enum ANIMATION {
		LINEAR("Core: linear"),
		SWING("Core: swing"),
		EASE_IN_QUAD("Quadratic: Ease In Quad"),
		EASE_OUT_QUAD("Quadratic: Ease Out Quad"),
		EASE_IN_OUT_QUAD("Quadratic: Ease InOut Quad"),
		EASE_IN_CUBIC("Cubic: Ease In Cubic"),
		EASE_OUT_CUBIC("Cubic: Ease Out Cubic"),
		EASE_IN_OUT_CUBIC("Cubic: Ease InOut Cubic"),
		EASE_IN_QUART("Quartic: Ease In Quart"),
		EASE_OUT_QUART("Quartic: Ease Out Quart"),
		EASE_IN_OUT_QUART("Quartic: Ease InOut Quart"),
		EASE_IN_QUINT("Quintic: Ease In Quint"),
		EASE_OUT_QUINT("Quintic: Ease Out Quint"),
		EASE_IN_OUT_QUINT("Quintic: Ease InOut Quint"),
		EASE_IN_SINE("Sine: Ease In Sine"),
		EASE_OUT_SINE("Sine: Ease Out Sine"),
		EASE_IN_OUT_SINE("Sine: Ease InOut Sine"),
		EASE_IN_EXPO("Exponential: Ease In Expo"),
		EASE_OUT_EXPO("Exponential: Ease Out Expo"),
		EASE_IN_OUT_EXPO("Exponential: Ease InOut Expo"),
		EASE_IN_CIRC("Circular: Ease In Circ"),
		EASE_OUT_CIRC("Circular: Ease Out Circ"),
		EASE_IN_OUT_CIRC("Circular: Ease InOut Circ"),
		EASE_IN_ELASTIC("Elastic: Ease In Elastic"),
		EASE_OUT_ELASTIC("Elastic: Ease Out Elastic"),
		EASE_IN_OUT_ELASTIC("Elastic: Ease InOut Elastic"),
		EASE_IN_BACK("Back: Ease In Back"),
		EASE_OUT_BACK("Back: Ease Out Back"),
		EASE_IN_OUT_BACK("Back: Ease InOut Back"),
		EASE_IN_BOUNCE("Bounce: Ease In Bounce"),
		EASE_OUT_BOUNCE("Bounce: Ease Out Bounce"),
		EASE_IN_OUT_BOUNCE("Bounce: Ease InOut Bounce");
		
	    private String description;
	    private ANIMATION (String description) {
	        this.description = description;
	    }
	    
	    @Override
	    public String toString() {
	        return description;
	    }

	}

package com.github.peholmst.touchkiti18n4vaadin;

import com.vaadin.ui.Component;

/**
 * An extended version of the Vaadin {@link Component}-interface that adds a
 * getter and setter method for an {@link I18N}-instance. Vaadin-components that
 * require internationalization should implement this interface.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public interface I18NComponent extends Component {

	/**
	 * Sets the <code>I18N</code> instance to use.
	 * 
	 * @param i18n
	 *            the <code>I18N</code> instance (may be <code>null</code>).
	 */
	void setI18N(I18N i18n);

	/**
	 * Gets the <code>I18N</code> instance to use.
	 * 
	 * @return the <code>I18N</code> instance, or <code>null</code> if none is
	 *         available.
	 */
	I18N getI18N();
}


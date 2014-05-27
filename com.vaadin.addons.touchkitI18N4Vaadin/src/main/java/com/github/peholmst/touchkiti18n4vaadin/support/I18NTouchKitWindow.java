package com.github.peholmst.touchkiti18n4vaadin.support;

import com.github.peholmst.touchkiti18n4vaadin.I18N;
import com.github.peholmst.touchkiti18n4vaadin.I18NComponent;
import com.vaadin.addon.touchkit.ui.TouchKitWindow;

/**
 * This is a convenience class that extends the standard Vaadin Window class and
 * adds I18N-support to it by implementing {@link I18NComponent}. Thus, all
 * components that are added to this window (either directly or through nesting)
 * and use {@link I18NComponentSupport} will automatically have access to the
 * window's {@link I18N}-instance through the
 * {@link I18NComponentSupport#getI18N()} method.
 * 
 * @see I18NComponent
 * @see I18NComponentSupport
 * @see I18N
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class I18NTouchKitWindow extends TouchKitWindow implements I18NComponent {

	private static final long serialVersionUID = -743406977553539377L;

	private final I18NComponentSupport i18nSupport = new I18NComponentSupport(
			this);

	/**
	 * Creates a new unnamed window with a default layout.
	 * 
	 * @param i18n
	 *            the I18N instance to use (may be <code>null</code>).
	 */
	public I18NTouchKitWindow(I18N i18n) {
		super();
		setI18N(i18n);
	}

	@Override
	public void setI18N(I18N i18n) {
		i18nSupport.setI18N(i18n);
	}

	@Override
	public I18N getI18N() {
		return i18nSupport.getI18N();
	}

}


package com.github.peholmst.touchkiti18n4vaadin;

import java.util.Locale;

/**
 * Listener interface to be implemented by classes that need to be notified when
 * the current locale of an {@link I18N} changes.
 * 
 * @see I18N#addListener(I18NListener)
 * @see I18N#setCurrentLocale(Locale)
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public interface I18NListener extends java.io.Serializable {

	/**
	 * Called when the current locale of an {@link I18N} has changed.
	 * 
	 * @param sender
	 *            the source of the event (never <code>null</code>).
	 * @param oldLocale
	 *            the old locale (may be <code>null</code>).
	 * @param newLocale
	 *            the new locale (may be <code>null</code>).
	 */
	void localeChanged(I18N sender, Locale oldLocale, Locale newLocale);
}


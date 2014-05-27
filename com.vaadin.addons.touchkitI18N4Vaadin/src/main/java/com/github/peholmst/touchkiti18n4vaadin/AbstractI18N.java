package com.github.peholmst.touchkiti18n4vaadin;

import java.util.LinkedList;
import java.util.Locale;

/**
 * This is an abstract base class for {@link I18N}-implementations that provide
 * support for adding/removing listeners and setting/getting the current locale.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public abstract class AbstractI18N implements I18N {

	private static final long serialVersionUID = -2502505781343501310L;

	private Locale currentLocale = null;

	private LinkedList<I18NListener> listenerList = new LinkedList<I18NListener>();

	@Override
	public void addListener(I18NListener listener) {
		if (listener != null) {
			listenerList.add(listener);
		}
	}

	@Override
	public void removeListener(I18NListener listener) {
		if (listener != null) {
			listenerList.remove(listener);
		}
	}

	@Override
	public Locale getCurrentLocale() {
		return currentLocale;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setCurrentLocale(Locale locale) throws IllegalArgumentException {
		Locale oldLocale = currentLocale;
		if (locale != null && !getSupportedLocales().contains(locale)) {
			throw new IllegalArgumentException("unsupported locale");
		}
		currentLocale = locale;
		LinkedList<I18NListener> clonedList = (LinkedList<I18NListener>) listenerList
				.clone();
		for (I18NListener l : clonedList) {
			l.localeChanged(this, oldLocale, currentLocale);
		}
	}
}

/*
 * generated by Xtext
 */
package org.blended.data.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

import org.blended.data.ui.internal.DataActivator;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class DataExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return DataActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return DataActivator.getInstance().getInjector(DataActivator.ORG_BLENDED_DATA_DATA);
	}
	
}
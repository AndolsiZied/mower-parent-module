package com.xebia.interview.command.di;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryProvider;
import com.xebia.interview.mower.core.Mower;

/**
 * Central class to provide implementation for the application.
 * 
 * @author Zied ANDOLSI
 * 
 */
public class BinderModule extends AbstractModule {

	/**
	 * {@inheritDoc}
	 */
	protected void configure() {
		bind(MachineFactory.class).toProvider(FactoryProvider.newFactory(MachineFactory.class, Mower.class));

	}

}

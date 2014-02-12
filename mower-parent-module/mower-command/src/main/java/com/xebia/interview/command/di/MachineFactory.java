package com.xebia.interview.command.di;

import com.xebia.interview.machine.api.Machine;
import com.xebia.interview.machine.api.Position;

/**
 * This interface defines methods for creating machine.
 * 
 * @author Zied ANDOLSI
 * 
 */
public interface MachineFactory {

	/**
	 * Instantiate new machine based on binder module.
	 * 
	 * @param position
	 *            positon
	 * @return {@link Machine}
	 */
	Machine create(Position position);

}

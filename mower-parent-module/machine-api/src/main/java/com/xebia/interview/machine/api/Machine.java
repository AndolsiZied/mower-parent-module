package com.xebia.interview.machine.api;

import java.util.List;

import com.xebia.interview.common.exception.IllegalParameterException;

/**
 * This interface defines methods for machines.
 * 
 * @author Zied ANDOLSI
 */
public interface Machine {

	/**
	 * Executes the list of instructions received as parameter
	 * 
	 * @param list
	 *            list of action
	 * @param borders
	 *            borders not to exceed
	 * @param positions
	 *            occupied position
	 * @return the new position which does not exceed the borders and which does not coincide with other position.
	 * @throws IllegalParameterException
	 *             thorwn when the value of an argument does not respect the excepted range of values
	 */
	Position execute(List<Action> list, Long[] borders, List<Position> positions) throws IllegalParameterException;

}

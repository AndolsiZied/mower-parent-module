package com.xebia.interview.command.api;

import com.xebia.interview.common.exception.IllegalParameterException;
import com.xebia.interview.common.exception.ResourceAccessException;

/**
 * This interface defines methods for managing command.
 * 
 * @author Zied ANDOLSI
 * 
 */
public interface Command {

	/**
	 * Returns the command's type.
	 * 
	 * @return the command's type
	 */
	String getType();

	/**
	 * Get the help.
	 * 
	 * @return The help message
	 */
	String getHelp();

	/**
	 * Process the command.
	 * 
	 * @param input
	 *            The parameters.
	 * @return The result of the command processing.
	 * @throws IllegalParameterException
	 *             thrown when the input parameters are invalid.
	 * @throws ResourceAccessException
	 *             thrown when error occurred while trying to access a resource
	 */
	String process(String[] input) throws IllegalParameterException, ResourceAccessException;
}

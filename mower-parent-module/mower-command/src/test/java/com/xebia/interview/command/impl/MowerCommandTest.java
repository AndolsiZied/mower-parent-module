package com.xebia.interview.command.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xebia.interview.common.exception.IllegalParameterException;
import com.xebia.interview.common.exception.ResourceAccessException;

/**
 * This class {@link MowerCommandTest} tests behavior of the {@link MowerCommand} methods.
 * 
 * @author Zied ANDOLSI
 * 
 */
public class MowerCommandTest {

	/**
	 * {@link MowerCommandTest}'s default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MowerCommandTest.class);

	/**
	 * Method testing process method of {@link MowerCommand} behavior, it tests with various input possibilities.
	 */
	@Test
	public void process() {
		LOGGER.info("Starting test process method...");

		MowerCommand mowerCommand = new MowerCommand();

		// 1. test with null input
		try {
			mowerCommand.process(null);
		} catch (IllegalParameterException e) {
			LOGGER.info("Missing operand : " + e.getLocalizedMessage());
			assertTrue(Boolean.TRUE);
		} catch (ResourceAccessException e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage());
			fail("Unexpected error : " + e.getLocalizedMessage());
		}

		// 2. test with empty input
		try {
			String[] input = { "" };
			mowerCommand.process(input);
		} catch (IllegalParameterException e) {
			LOGGER.info("Missing operand : " + e.getLocalizedMessage());
			assertTrue(Boolean.TRUE);
		} catch (ResourceAccessException e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage());
			fail("Unexpected error : " + e.getLocalizedMessage());
		}

		// 3. test with invalid input
		try {
			String[] input = { "invalid path" };
			mowerCommand.process(input);
		} catch (IllegalParameterException e) {
			LOGGER.info("Invalid file path : " + e.getLocalizedMessage());
			assertTrue(Boolean.TRUE);
		} catch (ResourceAccessException e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage());
			fail("Unexpected error : " + e.getLocalizedMessage());
		}

		// 4. test with empty file
		try {
			String[] input = { this.getClass().getResource("/invalid/empty-file").getPath() };
			String result = mowerCommand.process(input);
			assertNotNull("Result must not be null", result);
			String[] errors = result.split("\\n");
			assertTrue("Error list size must contain a single error", errors.length == 1);
		} catch (IllegalParameterException e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage());
			fail("Unexpected error : " + e.getLocalizedMessage());
		} catch (ResourceAccessException e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage());
			fail("Unexpected error : " + e.getLocalizedMessage());
		}

		// 5. test with invalid border coordinates
		try {
			String[] input = { this.getClass().getResource("/invalid/invalid-file-1").getPath() };
			String result = mowerCommand.process(input);
			assertNotNull("Result must not be null", result);
			String[] errors = result.split("\\n");
			assertTrue("Error list size must contain a single error", errors.length == 1);
		} catch (IllegalParameterException e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage());
			fail("Unexpected error : " + e.getLocalizedMessage());
		} catch (ResourceAccessException e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage());
			fail("Unexpected error : " + e.getLocalizedMessage());
		}

		// 6. test with invalid mower position
		try {
			String[] input = { this.getClass().getResource("/invalid/invalid-file-2").getPath() };
			String result = mowerCommand.process(input);
			assertNotNull("Result must not be null", result);
			String[] errors = result.split("\\n");
			assertTrue("Error list size must contain 2 errors", errors.length == 2);
		} catch (IllegalParameterException e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage());
			fail("Unexpected error : " + e.getLocalizedMessage());
		} catch (ResourceAccessException e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage());
			fail("Unexpected error : " + e.getLocalizedMessage());
		}

		// 7. test with invalid mower instructions
		try {
			String[] input = { this.getClass().getResource("/invalid/invalid-file-3").getPath() };
			String result = mowerCommand.process(input);
			assertNotNull("Result must not be null", result);
			String[] errors = result.split("\\n");
			assertTrue("Error list size must contain 2 errors", errors.length == 2);
		} catch (IllegalParameterException e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage());
			fail("Unexpected error : " + e.getLocalizedMessage());
		} catch (ResourceAccessException e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage());
			fail("Unexpected error : " + e.getLocalizedMessage());
		}

		// 8. test with valide mower instructions
		try {
			String[] input = { this.getClass().getResource("/valid/valid-file-1").getPath() };
			String result = mowerCommand.process(input);
			assertNotNull("Result must not be null", result);
			String[] positions = result.split("\\n");
			assertTrue("Position list size must contain 2 element", positions.length == 2);
			assertTrue("Position of the first mower must be 1 3 N", positions[0].equals("1 3 N"));
			assertTrue("Position of the seconde mower must be 5 1 E", positions[1].equals("5 1 E"));
		} catch (IllegalParameterException e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage());
			fail("Unexpected error : " + e.getLocalizedMessage());
		} catch (ResourceAccessException e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage());
			fail("Unexpected error : " + e.getLocalizedMessage());
		}

		LOGGER.info("Testing process method ends.");
	}
}

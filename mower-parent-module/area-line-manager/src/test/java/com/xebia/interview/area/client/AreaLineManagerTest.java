package com.xebia.interview.area.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xebia.interview.area.util.Constants;

/**
 * This class {@link AreaLineManagerTest} tests behavior of the {@link AreaLineManager} methods.
 * 
 * @author Zied ANDOLSI
 * 
 */
public class AreaLineManagerTest {

	/**
	 * {@link AreaLineManagerTest}'s default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AreaLineManagerTest.class);

	/**
	 * Method testing execute method of {@link AreaLineManager} behavior, it tests with various command possibilities.
	 */
	@Test
	public void execute() {
		LOGGER.info("Starting test execute method...");
		AreaLineManager gardenLineManager = new AreaLineManager();
		gardenLineManager.initCommands();
		String result;

		// 1. null input
		LOGGER.debug("testing null input...");
		try {
			result = gardenLineManager.execute(null);
			assertEquals(result, "");
		} catch (Exception e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage(), e);
			fail("Unexpected error : " + e.getLocalizedMessage());
		}

		// 2. empty input
		LOGGER.debug("testing empty input...");
		try {
			result = gardenLineManager.execute("");
			assertEquals(result, "");
		} catch (Exception e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage(), e);
			fail("Unexpected error : " + e.getLocalizedMessage());
		}

		// 3. exit command
		LOGGER.debug("testing exit command...");
		try {
			result = gardenLineManager.execute(gardenLineManager.getExitCommand());
			assertNull(result);
		} catch (Exception e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage(), e);
			fail("Unexpected error : " + e.getLocalizedMessage());
		}

		// 4. help command
		LOGGER.debug("testing help command...");
		try {
			result = gardenLineManager.execute(gardenLineManager.getHelpCommand());
			assertEquals(helpCommandReturn(gardenLineManager), result);
		} catch (Exception e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage(), e);
			fail("Unexpected error : " + e.getLocalizedMessage());
		}

		// 5. invalid command
		LOGGER.debug("testing invalid command...");
		try {
			result = gardenLineManager.execute("do nothing");
			assertEquals(gardenLineManager.getPropertiesLoader().loadProperty(Constants.MAIN_INVALID_COMMAND), result);
		} catch (Exception e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage(), e);
			fail("Unexpected error : " + e.getLocalizedMessage());
		}

		// 5. valid command
		LOGGER.debug("testing valid command...");
		try {
			result =
					gardenLineManager.execute("tondre " + this.getClass().getResource("/valid/valid-file-1").getPath());
			assertEquals("1 3 N\n5 1 E", result);
		} catch (Exception e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage(), e);
			fail("Unexpected error : " + e.getLocalizedMessage());
		}

		// 6. valid command (file name contains espace)
		LOGGER.debug("testing valid command (file name contains espace)...");
		try {
			String pathWithEspace = "\"" + this.getClass().getResource("/valid/valid file-2").getPath() + "\"";
			result = gardenLineManager.execute("tondre " + pathWithEspace.replace("%20", " "));
			assertEquals("1 3 N\n5 1 E", result);
		} catch (Exception e) {
			LOGGER.error("Unexpected error : " + e.getLocalizedMessage(), e);
			fail("Unexpected error : " + e.getLocalizedMessage());
		}

		LOGGER.info("Testing execute method ends.");
	}

	/**
	 * Resturns message to be displayed after executing help command.
	 * 
	 * @param gardenLineManager
	 *            instance of {@link AreaLineManager}.
	 * @return message to be displayed.
	 */
	private String helpCommandReturn(AreaLineManager gardenLineManager) {
		StringBuilder content = new StringBuilder();
		content.append(gardenLineManager.getPropertiesLoader().loadProperty(Constants.MAIN_AVAILABLE_COMMANDS));
		for (String availableCommand : gardenLineManager.getCommands().keySet()) {
			content.append("\n");
			content.append(Constants.DASH + availableCommand);
		}
		return content.toString();
	}
}

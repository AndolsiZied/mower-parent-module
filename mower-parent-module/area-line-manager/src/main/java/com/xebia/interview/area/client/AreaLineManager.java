package com.xebia.interview.area.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xebia.interview.area.util.Constants;
import com.xebia.interview.command.api.Command;
import com.xebia.interview.common.exception.IllegalParameterException;
import com.xebia.interview.common.exception.ResourceAccessException;
import com.xebia.interview.common.util.CommonUtil;
import com.xebia.interview.common.util.PropertiesLoader;

/**
 * Main class for managing area contents.
 * <p>
 * Once the main method is called, it must type the command quit to stop the program.
 * 
 * @author Zied ANDOLSI
 * 
 */
public class AreaLineManager {

	/**
	 * {@link AreaLineManager}'s default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AreaLineManager.class);

	/**
	 * {@link AreaLineManager}'s console logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger("console");

	/**
	 * Available commands.
	 */
	private Map<String, Command> commands;

	/**
	 * Help command.
	 */
	private String helpCommand;

	/**
	 * Exit command.
	 */
	private String exitCommand;

	/**
	 * Properties loader.
	 */
	private PropertiesLoader propertiesLoader = new PropertiesLoader(Constants.MSG_RESOURCES_NAME);

	/**
	 * Main method.
	 * 
	 * @param args
	 *            program arguments
	 */
	public static void main(String[] args) {
		AreaLineManager commandLine = new AreaLineManager();
		commandLine.initCommands();
		commandLine.launch();
	}

	/**
	 * Application entry Method after initialization.
	 */
	public void launch() {
		LOGGER.debug("Method launch starting...");

		// 1. Display welcome message
		LOG.debug(propertiesLoader.loadProperty(Constants.MAIN_WELCOME));
		LOG.debug(propertiesLoader.loadProperty(Constants.MAIN_START));
		LOG.debug(MessageFormat.format(propertiesLoader.loadProperty(Constants.MAIN_HELP), helpCommand));
		LOG.debug(MessageFormat.format(propertiesLoader.loadProperty(Constants.MAIN_EXIT), exitCommand));

		// 2. Read and process command
		readAndExecute(System.in);

		// 3. Display bye message
		LOG.debug(propertiesLoader.loadProperty(Constants.MAIN_BYE));

		LOGGER.debug("Method launch ends.");

	}

	/**
	 * Reads and executes command from input stream.
	 * 
	 * @param in
	 *            input stream.
	 */
	public void readAndExecute(InputStream in) {
		BufferedReader reader = null;
		String line = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
			while (true) {
				LOGGER.debug("Reading console input...");

				LOG.debug(Constants.PROMPT);
				line = reader.readLine();

				LOGGER.debug("Received command [ " + line + " ]");

				String result = execute(line);

				if (result == null) {
					break;
				}

				LOGGER.debug("Print out the result of the command processing");

				LOG.debug(result);
			}
		} catch (IOException e) {
			LOGGER.error("Error occurred when trying to read the console input : " + e.getLocalizedMessage(), e);

			LOG.debug(propertiesLoader.loadProperty(Constants.MAIN_APPLICATION_ERROR));
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					LOGGER.error("Error occurred when trying to close the input reader : " + e.getLocalizedMessage(), e);
				}
			}
		}
	}

	/**
	 * Executes the received command.
	 * 
	 * @param line
	 *            command and arguments.
	 * @return message to be displayed
	 */
	public String execute(String line) {
		String[] lineTokens = CommonUtil.split(line);
		StringBuilder result = new StringBuilder("");
		if (lineTokens.length > 0) {
			String commandName = lineTokens[0];

			// 1. Process the received command
			LOGGER.debug("Process the received command...");

			if (exitCommand.equals(commandName)) {
				return null;
			}

			if (helpCommand.equals(commandName)) {
				if (commands.isEmpty()) {
					return propertiesLoader.loadProperty(Constants.MAIN_NO_AVAILABLE_COMMANDS);
				}
				result.append(propertiesLoader.loadProperty(Constants.MAIN_AVAILABLE_COMMANDS));
				for (String availableCommand : commands.keySet()) {
					result.append("\n");
					result.append(Constants.DASH + availableCommand);
				}

				return result.toString();
			}

			Command command = commands.get(commandName);
			if (command == null) {
				LOGGER.error("Unkhwon command [ " + commandName + " ]");
				result.append(propertiesLoader.loadProperty(Constants.MAIN_INVALID_COMMAND));
			} else {

				// 2. Executing command
				LOGGER.debug("Executing command [ " + commandName + " ]");
				try {
					result.append(command.process((String[]) ArrayUtils.removeElement(lineTokens, commandName)));
				} catch (IllegalParameterException e) {
					LOGGER.error("Invalid syntax : " + e.getLocalizedMessage(), e);
					result.append(propertiesLoader.loadProperty(Constants.MAIN_INVALID_SYNTAX) + "\n"
							+ command.getHelp());
				} catch (ResourceAccessException e) {
					LOGGER.error("Error occurred when trying to access file : " + e.getLocalizedMessage(), e);
					result.append(propertiesLoader.loadProperty(Constants.MAIN_ERROR_ACCESS) + "\n" + command.getHelp());
				}

			}
		}
		return result.toString();
	}

	/**
	 * This method discovers and saves the available commands.
	 * <p>
	 * Help command and exit command are loaded from properties file. Discovery and loading of other commands is
	 * provided by the ServiceLoader.
	 */
	public void initCommands() {
		LOGGER.debug("method initCommands starting...");

		// 1. Initialize the standard commands
		exitCommand = propertiesLoader.loadProperty(Constants.MAIN_EXIT_COMMAND);
		helpCommand = propertiesLoader.loadProperty(Constants.MAIN_HELP_COMMAND);

		commands = new HashMap<String, Command>();

		// 2. Load available commands
		ServiceLoader<Command> commandLoader = ServiceLoader.load(Command.class);
		commandLoader.reload();

		// 3. Map commands to its type.
		Iterator<Command> commandsIterator = commandLoader.iterator();
		while (commandsIterator.hasNext()) {
			Command command = commandsIterator.next();
			commands.put(command.getType(), command);
		}

	}

	/**
	 * Get help command.
	 * 
	 * @return helpCommand
	 */
	public String getHelpCommand() {
		return helpCommand;
	}

	/**
	 * Get exit command.
	 * 
	 * @return exit command
	 */
	public String getExitCommand() {
		return exitCommand;
	}

	/**
	 * Get properties loader.
	 * 
	 * @return {@link PropertiesLoader}
	 */
	public PropertiesLoader getPropertiesLoader() {
		return propertiesLoader;
	}

	/**
	 * Get commands map.
	 * 
	 * @return map commands to its type
	 */
	public Map<String, Command> getCommands() {
		return commands;
	}

}

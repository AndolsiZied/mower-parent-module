package com.xebia.interview.command.impl;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.xebia.interview.command.api.Command;
import com.xebia.interview.command.di.BinderModule;
import com.xebia.interview.command.di.MachineFactory;
import com.xebia.interview.command.util.Constants;
import com.xebia.interview.common.exception.IllegalParameterException;
import com.xebia.interview.common.exception.ResourceAccessException;
import com.xebia.interview.common.util.PropertiesLoader;
import com.xebia.interview.machine.api.Action;
import com.xebia.interview.machine.api.Machine;
import com.xebia.interview.machine.api.Orientation;
import com.xebia.interview.machine.api.Position;

/**
 * This class implements the Command operations for the specified type.
 * 
 * @author Zied ANDOLSI
 * 
 */
public class MowerCommand implements Command {

	/**
	 * {@link MowerCommand}'s default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MowerCommand.class);

	/** position list key. **/
	private static final String POSITION_LIST = "position_list";

	/** border list key. **/
	private static final String BORDER_LIST = "border_list";

	/** error list key. **/
	private static final String ERROR_LIST = "error_list";

	/** instruction list key. **/
	private static final String INSTRUCTION_LIST = "instruction_list";

	/** orientation map. **/
	private Map<String, String> orientationMap;

	/** action map. **/
	private Map<String, String> actionMap;

	/** Properties loader. **/
	private PropertiesLoader propertiesLoader = new PropertiesLoader(Constants.CONFIG_RESOURCES_NAME);

	/**
	 * {@inheritDoc}
	 */
	public String getType() {
		return propertiesLoader.loadProperty(Constants.COMMAND_NAME);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getHelp() {
		return propertiesLoader.loadProperty(Constants.COMMAND_HELP);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public String process(String[] input) throws IllegalParameterException, ResourceAccessException {
		LOGGER.debug("Starting process method...");

		// 1. Checking input parameter
		LOGGER.debug("Checking input parameter...");
		if ((input == null) || (input.length == 0)) {
			throw new IllegalParameterException("Missing operand.");
		}

		String fileName = input[0];
		if (fileName == null || fileName.trim().length() == 0) {
			throw new IllegalParameterException("Missing operand.");
		}
		File inputFile = new File(fileName.replace("\"", ""));
		if (!inputFile.exists() || !inputFile.isFile()) {
			throw new IllegalParameterException("The received argument doesn't exist or is not a file.");
		}

		// 2. Getting file content
		LOGGER.debug("Getting file content...");
		List<String> orders = null;
		try {
			String content = FileUtils.readFileToString(inputFile);
			String[] lines = content.split("\\n");
			orders = Arrays.asList(lines);
		} catch (IOException e) {
			LOGGER.error("Error occurred when trying to get content file: " + e.getLocalizedMessage());
			throw new ResourceAccessException("Error occurred when trying to get content file", e);
		}

		StringBuilder result = new StringBuilder();

		// 3. Validate file content
		LOGGER.debug("Validating the file content...");
		Map<String, List<?>> resultMap = validateContent(orders);
		List<String> errorList = (List<String>) resultMap.get(ERROR_LIST);
		if (errorList != null && !errorList.isEmpty()) {
			for (String error : errorList) {
				result.append(error);
				result.append("\n");
			}
			LOGGER.debug("Returning error message...");
			return result.toString().trim();
		}

		// 4. Processing instructions
		LOGGER.debug("Processing instructions...");
		List<Position> positions = (List<Position>) resultMap.get(POSITION_LIST);
		List<List<Action>> instructions = (List<List<Action>>) resultMap.get(INSTRUCTION_LIST);
		List<Long> borders = (List<Long>) resultMap.get(BORDER_LIST);

		Injector injector = Guice.createInjector(new BinderModule());
		MachineFactory machineFactory = injector.getInstance(MachineFactory.class);

		for (int i = 0; i < positions.size(); i++) {
			Machine machine = machineFactory.create(positions.get(i));
			Position position = machine.execute(instructions.get(i), (Long[]) borders.toArray(new Long[2]), positions);
			result.append(position.getX() + " " + position.getY() + " "
					+ propertiesLoader.loadProperty(position.getOrientation().getKey()));
			result.append("\n");
		}
		LOGGER.debug("Returning result processing...");
		return result.toString().trim();
	}

	private Map<String, List<?>> validateContent(List<String> lines) {
		LOGGER.debug("Starting validateContent method...");

		List<String> errorList = new ArrayList<String>();
		List<Position> positions = new ArrayList<Position>();
		List<List<Action>> instructions = new ArrayList<List<Action>>();
		List<Long> borders = new ArrayList<Long>();

		// 1. validate upper corner coordinates
		String upperCornerPattern = propertiesLoader.loadProperty(Constants.UPPER_CORNER_PATTERN);
		String firstLine = lines.get(0);
		boolean matched = Pattern.matches(upperCornerPattern, firstLine);
		if (matched) {
			String[] cooridnates = firstLine.split("\\s");
			borders.add(Long.valueOf(cooridnates[0]));
			borders.add(Long.valueOf(cooridnates[1]));

			// 2.1 validate mower position
			int index = 1;
			String mowerInitPosPattern = loadMowerInitPositionPattern();
			String mowerOrdersPattern = loadMowerOrdersPattern();

			while (index < lines.size()) {
				String mowerPosition = lines.get(index);
				matched = Pattern.matches(mowerInitPosPattern, mowerPosition);
				if (matched) {

					cooridnates = mowerPosition.split("\\s");
					Position newPosition =
							new Position(Long.valueOf(cooridnates[0]), Long.valueOf(cooridnates[1]),
									Orientation.valueOf(loadKeyOfOrientation(cooridnates[2]).toUpperCase()));

					// 2.2 check if this position is busy or is outside the borders
					boolean checked = true;
					if (newPosition.getX() > borders.get(0) || newPosition.getY() > borders.get(1)) {
						checked = false;
					} else {
						for (Position position : positions) {
							if (newPosition.equals(position)) {
								checked = false;
								break;
							}
						}
					}
					if (!checked) {
						LOGGER.error("The position of the mower [ " + ((index / 2) + 1)
								+ " ] exceeds the borders or is busy.");
						errorList.add(MessageFormat.format(
								propertiesLoader.loadProperty(Constants.ERROR_MOWER_POS_OCCUPIED), (index / 2) + 1));
					} else {
						// 2.3 save position
						positions.add(newPosition);
					}

				} else {
					LOGGER.error("The position of the mower [ " + ((index / 2) + 1) + " ] is invalid.");
					errorList.add(MessageFormat.format(
							propertiesLoader.loadProperty(Constants.ERROR_MOWER_POS_PATTERN), (index / 2) + 1));
				}

				// 3.1 validate mower instructions
				String mowerOrders = lines.get(index + 1);
				matched = Pattern.matches(mowerOrdersPattern, mowerOrders);
				if (!matched) {
					LOGGER.error("The mower instruction serie [ " + ((index / 2) + 1) + " ] is invalid.");
					errorList.add(MessageFormat.format(
							propertiesLoader.loadProperty(Constants.ERROR_MOWER_ORDERS_PATTERN), (index / 2) + 1));
				} else {
					char[] actions = mowerOrders.toCharArray();
					List<Action> actionList = new ArrayList<Action>();
					for (char action : actions) {
						actionList.add(Action.valueOf(loadKeyOfAction(action + "").toUpperCase()));
					}
					// 3.2 save instructions
					instructions.add(actionList);
				}
				index += 2;
			}
		} else {
			LOGGER.error("The first line doesn't match the pattern [ " + upperCornerPattern + " ]");
			errorList.add(MessageFormat.format(propertiesLoader.loadProperty(Constants.ERROR_UPPER_CORNER_PATTERN),
					upperCornerPattern));
		}

		// 4. Returning validation result
		Map<String, List<?>> resultMap = new HashMap<String, List<?>>();
		if (!errorList.isEmpty()) {
			resultMap.put(ERROR_LIST, errorList);
			return resultMap;
		}
		resultMap.put(POSITION_LIST, positions);
		resultMap.put(BORDER_LIST, borders);
		resultMap.put(INSTRUCTION_LIST, instructions);
		LOGGER.debug("Returning validation result...");
		return resultMap;

	}

	/**
	 * Loads mower order pattern.
	 * 
	 * @return loaded pattern.
	 */
	private String loadMowerOrdersPattern() {
		String mowerOrdersPattern = propertiesLoader.loadProperty(Constants.MOWER_ORDERS_PATTERN);
		String advance = propertiesLoader.loadProperty(Action.ADVANCE.getKey());
		String right = propertiesLoader.loadProperty(Action.RIGHT.getKey());
		String left = propertiesLoader.loadProperty(Action.LEFT.getKey());
		return MessageFormat.format(mowerOrdersPattern, advance.concat(right).concat(left));
	}

	/**
	 * Loads mower initial pattern.
	 * 
	 * @return loaded pattern.
	 */
	private String loadMowerInitPositionPattern() {
		String mowerInitPosPattern = propertiesLoader.loadProperty(Constants.MOWER_INIT_POS_PATTERN);
		String north = propertiesLoader.loadProperty(Orientation.NORTH.getKey());
		String west = propertiesLoader.loadProperty(Orientation.WEST.getKey());
		String south = propertiesLoader.loadProperty(Orientation.SOUTH.getKey());
		String east = propertiesLoader.loadProperty(Orientation.EAST.getKey());
		return MessageFormat.format(mowerInitPosPattern, north.concat(south).concat(east).concat(west));
	}

	/**
	 * Loads key of orientation value.
	 * 
	 * @param value
	 *            orientation value
	 * @return orientation key
	 */
	private String loadKeyOfOrientation(String value) {
		if (orientationMap == null) {
			orientationMap = new HashMap<String, String>();
			orientationMap.put(propertiesLoader.loadProperty(Orientation.EAST.getKey()), Orientation.EAST.getKey());
			orientationMap.put(propertiesLoader.loadProperty(Orientation.NORTH.getKey()), Orientation.NORTH.getKey());
			orientationMap.put(propertiesLoader.loadProperty(Orientation.SOUTH.getKey()), Orientation.SOUTH.getKey());
			orientationMap.put(propertiesLoader.loadProperty(Orientation.WEST.getKey()), Orientation.WEST.getKey());
		}
		return orientationMap.get(value);
	}

	/**
	 * Loads key of action value.
	 * 
	 * @param value
	 *            action value
	 * @return action key
	 */
	private String loadKeyOfAction(String value) {
		if (actionMap == null) {
			actionMap = new HashMap<String, String>();
			actionMap.put(propertiesLoader.loadProperty(Action.ADVANCE.getKey()), Action.ADVANCE.getKey());
			actionMap.put(propertiesLoader.loadProperty(Action.LEFT.getKey()), Action.LEFT.getKey());
			actionMap.put(propertiesLoader.loadProperty(Action.RIGHT.getKey()), Action.RIGHT.getKey());
		}
		return actionMap.get(value);
	}
}

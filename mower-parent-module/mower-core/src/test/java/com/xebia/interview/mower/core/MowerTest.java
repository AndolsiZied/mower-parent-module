package com.xebia.interview.mower.core;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xebia.interview.common.exception.IllegalParameterException;
import com.xebia.interview.machine.api.Action;
import com.xebia.interview.machine.api.Orientation;
import com.xebia.interview.machine.api.Position;

/**
 * This class {@link MowerTest} tests behavior of the {@link Mower} methods.
 * 
 * @author Zied ANDOLSI
 * 
 */
public class MowerTest {

	/**
	 * {@link MowerTest}'s default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MowerTest.class);

	/**
	 * Method testing execute method of {@link Mower} behavior, it tests with various input possibilities.
	 */
	@Test
	public void execute() {
		LOGGER.info("Starting test execute method...");

		Position position = new Position(1, 1, Orientation.NORTH);
		Mower mower = new Mower(position);
		Long[] borders = { 3L, 3L };
		List<Action> instrcutions = new ArrayList<Action>();
		List<Position> positions = new ArrayList<Position>();

		// 1. test with missing borders
		try {
			mower.execute(instrcutions, null, positions);
			fail("must throw exception before thsi line");
		} catch (IllegalParameterException e) {
			LOGGER.debug("Borders cannot be null");
			assertTrue(Boolean.TRUE);
		}

		// 2. test with missing instructins
		try {
			Position newPosition = mower.execute(null, borders, positions);
			assertTrue("mower must remain in the same position", position.equals(newPosition));
		} catch (IllegalParameterException e) {
			LOGGER.error("Error occurred when trying to execute instructions : " + e.getLocalizedMessage());
			fail("Error occurred when trying to execute instructions : " + e.getLocalizedMessage());
		}

		instrcutions.add(Action.ADVANCE);
		instrcutions.add(Action.RIGHT);
		instrcutions.add(Action.ADVANCE);
		instrcutions.add(Action.LEFT);
		// 3. perfect scenario
		try {
			Position newPosition = mower.execute(instrcutions, borders, positions);
			Position expectedPosition = new Position(2, 2, Orientation.NORTH);
			assertTrue("mower must not be in this position", newPosition.equals(expectedPosition));
		} catch (IllegalParameterException e) {
			LOGGER.error("Error occurred when trying to execute instructions : " + e.getLocalizedMessage());
			fail("Error occurred when trying to execute instructions : " + e.getLocalizedMessage());
		}

		// 4. the mower exceeds borders
		instrcutions.add(Action.ADVANCE);
		instrcutions.add(Action.ADVANCE);
		instrcutions.add(Action.ADVANCE);
		instrcutions.add(Action.RIGHT);
		instrcutions.add(Action.ADVANCE);
		instrcutions.add(Action.ADVANCE);
		instrcutions.add(Action.RIGHT);
		position.setX(1);
		position.setY(1);
		position.setOrientation(Orientation.NORTH);
		positions.add(position);
		try {
			Position newPosition = mower.execute(instrcutions, borders, positions);
			Position expectedPosition = new Position(3, 3, Orientation.SOUTH);
			assertTrue("mower must not be in this position", newPosition.equals(expectedPosition));
		} catch (IllegalParameterException e) {
			LOGGER.error("Error occurred when trying to execute instructions : " + e.getLocalizedMessage());
			fail("Error occurred when trying to execute instructions : " + e.getLocalizedMessage());
		}

		// 5. the mower passes a busy position
		position.setX(1);
		position.setY(1);
		position.setOrientation(Orientation.NORTH);
		positions.add(position);
		positions.add(new Position(2, 3, Orientation.NORTH));
		try {
			Position newPosition = mower.execute(instrcutions, borders, positions);
			Position expectedPosition = new Position(3, 2, Orientation.SOUTH);
			assertTrue("mower must not be in this position", newPosition.equals(expectedPosition));
		} catch (IllegalParameterException e) {
			LOGGER.error("Error occurred when trying to execute instructions : " + e.getLocalizedMessage());
			fail("Error occurred when trying to execute instructions : " + e.getLocalizedMessage());
		}

		LOGGER.info("Testing execute method ends.");

	}
}

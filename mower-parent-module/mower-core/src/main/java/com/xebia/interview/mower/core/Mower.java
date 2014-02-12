package com.xebia.interview.mower.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.xebia.interview.common.exception.IllegalParameterException;
import com.xebia.interview.common.exception.NotRespectedRulesException;
import com.xebia.interview.machine.api.AbstractMachine;
import com.xebia.interview.machine.api.Action;
import com.xebia.interview.machine.api.Position;

/**
 * This class implements the machine operations for the specified type.
 * 
 * @author Zied ANDOLSI
 * 
 */
public class Mower extends AbstractMachine {

	/**
	 * {@link Mower}'s default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Mower.class);

	/**
	 * Constructs a new <code>Mower</code>.It initialize the position.
	 */
	@Inject
	public Mower(@Assisted Position position) {
		super(position);
	}

	/**
	 * {@inheritDoc}
	 */
	public Position execute(List<Action> actions, Long[] borders, List<Position> positions)
			throws IllegalParameterException {
		LOGGER.debug("Starting execute method...");

		// 1. Checking input parameter
		LOGGER.debug("Checking input parameter...");
		if (borders == null || borders.length != 2) {
			throw new IllegalParameterException("Missing area borders.");
		}

		// 2. Processing
		if (actions != null && !actions.isEmpty()) {
			for (Action action : actions) {
				position = move(action, borders, positions);
			}
		}
		LOGGER.debug("Method execute ends.");
		return position;
	}

	private Position move(Action action, Long[] borders, List<Position> positions) {

		Position newPosition;
		try {
			newPosition = action.changePosition(position);
		} catch (NotRespectedRulesException e) {
			LOGGER.error("The position state is inconsistent : " + e.getLocalizedMessage(), e);
			return position;
		}

		// 1. check border
		if (newPosition.getX() > borders[0] || newPosition.getX() < 0 || newPosition.getY() > borders[1]
				|| newPosition.getY() < 0) {
			return position;
		}
		if (positions != null && !positions.isEmpty()) {
			// 2. check if position is busy
			for (Position occupiedPosition : positions) {
				if (position != occupiedPosition && newPosition.equals(occupiedPosition)) {
					return position;
				}
			}
		}

		// 3. update position
		position.setX(newPosition.getX());
		position.setY(newPosition.getY());
		position.setOrientation(newPosition.getOrientation());
		LOGGER.debug("new position [ " + position.getX() + " " + position.getY() + " "
				+ position.getOrientation().getKey() + " ]");
		return position;
	}
}

package com.xebia.interview.machine.api;

import com.xebia.interview.common.exception.NotRespectedRulesException;

/**
 * Enumeration defines action possible values.
 * 
 * @author Zied ANDOLSI
 * 
 */
public enum Action {
	ADVANCE("advance") {

		/**
		 * {@inheritDoc}
		 */
		public Position changePosition(Position position) throws NotRespectedRulesException {
			switch (position.getOrientation()) {
			case NORTH:
				return new Position(position.getX(), position.getY() + 1, position.getOrientation());
			case EAST:
				return new Position(position.getX() + 1, position.getY(), position.getOrientation());
			case SOUTH:
				return new Position(position.getX(), position.getY() - 1, position.getOrientation());
			case WEST:
				return new Position(position.getX() - 1, position.getY(), position.getOrientation());
			default:
				throw new NotRespectedRulesException("Unexpected orientation [ " + position.getOrientation() + " ]");
			}

		}
	},
	RIGHT("right") {

		/**
		 * {@inheritDoc}
		 */
		public Position changePosition(Position position) throws NotRespectedRulesException {
			switch (position.getOrientation()) {
			case NORTH:
				return new Position(position.getX(), position.getY(), Orientation.EAST);
			case EAST:
				return new Position(position.getX(), position.getY(), Orientation.SOUTH);
			case SOUTH:
				return new Position(position.getX(), position.getY(), Orientation.WEST);
			case WEST:
				return new Position(position.getX(), position.getY(), Orientation.NORTH);
			default:
				throw new NotRespectedRulesException("Unexpected orientation [ " + position.getOrientation() + " ]");
			}
		}
	},
	LEFT("left") {

		/**
		 * {@inheritDoc}
		 */
		public Position changePosition(Position position) throws NotRespectedRulesException {
			switch (position.getOrientation()) {
			case NORTH:
				return new Position(position.getX(), position.getY(), Orientation.WEST);
			case EAST:
				return new Position(position.getX(), position.getY(), Orientation.NORTH);
			case SOUTH:
				return new Position(position.getX(), position.getY(), Orientation.EAST);
			case WEST:
				return new Position(position.getX(), position.getY(), Orientation.SOUTH);
			default:
				throw new NotRespectedRulesException("Unexpected orientation [ " + position.getOrientation() + " ]");
			}
		}
	};

	/**
	 * Key attribute.
	 */
	private String key;

	/**
	 * Constructor with key parameter.
	 * 
	 * @param key
	 *            property key
	 */
	private Action(String key) {
		this.key = key;
	}

	/**
	 * Get the key.
	 * 
	 * @return key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Changes the position.
	 * 
	 * @param position
	 *            {@link Position}
	 * @return new position
	 * @throws NotRespectedRulesException
	 *             thrown when the state of the received parameter is not consistent.
	 */
	public abstract Position changePosition(Position position) throws NotRespectedRulesException;

}

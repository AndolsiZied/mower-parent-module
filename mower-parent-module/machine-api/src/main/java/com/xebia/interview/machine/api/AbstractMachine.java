package com.xebia.interview.machine.api;

/**
 * Abstract class represents a base for machines.
 * 
 * @author Zied ANDOLSI
 * 
 */
public abstract class AbstractMachine implements Machine {

	/**
	 * Position attribute of the machine.
	 */
	protected Position position;

	/**
	 * Constructs a new <code>Machine</code>.It initialize the position.
	 */
	protected AbstractMachine(Position position) {
		this.position = position;
	}

	/**
	 * Get position.
	 * 
	 * @return {@link Position}
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Set the position.
	 * 
	 * @param position
	 *            {@link Position}
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

}

package com.xebia.interview.machine.api;

/**
 * Enumeration defines orientation possible values.
 * 
 * @author Zied ANDOLSI
 * 
 */
public enum Orientation {

	NORTH("north"), SOUTH("south"), EAST("east"), WEST("west");

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
	private Orientation(String key) {
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

}

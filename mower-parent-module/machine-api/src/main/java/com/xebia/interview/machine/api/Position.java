package com.xebia.interview.machine.api;

/**
 * Represents a location within a flat area.
 * <p>
 * It's designed by a horizontal coordinate, a vertical coordinate and a direction.
 * 
 * @author Zied ANDOLSI
 * 
 */
public class Position {

	/**
	 * horizontal coordinate.
	 */
	private long x;

	/**
	 * vertical coordinate.
	 */
	private long y;

	/**
	 * Position's direction.
	 */
	private Orientation orientation;

	/**
	 * Constructs new <code>Position</code>. It initialize horizontal coordinate, vertical coordinate and the direction.
	 * 
	 * @param x
	 *            horizontal coordinate
	 * @param y
	 *            vertical coordinate
	 * @param orientation
	 *            orientation
	 */
	public Position(long x, long y, Orientation orientation) {
		this.x = x;
		this.y = y;
		this.orientation = orientation;
	}

	/**
	 * Set the horizontal coordinate.
	 * 
	 * @param x
	 *            horizontal coordinate
	 */
	public void setX(long x) {
		this.x = x;
	}

	/**
	 * Set the vertical coordinate.
	 * 
	 * @param x
	 *            vertical coordinate
	 */
	public void setY(long y) {
		this.y = y;
	}

	/**
	 * Set the orientation.
	 * 
	 * @param orientation
	 *            orientation
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * Get the horizontal coordinate.
	 * 
	 * @return position's x
	 */
	public long getX() {
		return x;
	}

	/**
	 * Get the vertical coordinate.
	 * 
	 * @return position's y
	 */
	public long getY() {
		return y;
	}

	/**
	 * Get the orientation.
	 * 
	 * @return position's orientation
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
		result = prime * result + (int) (x ^ (x >>> 32));
		result = prime * result + (int) (y ^ (y >>> 32));
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Position other = (Position) obj;
		if (orientation != other.orientation) {
			return false;
		}
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

}

package com.xebia.interview.common.exception;

/**
 * The {@link ResourceAccessException} exception should be thrown when an error occurs while trying to access a resource
 * (file, URL or other).
 * 
 * @author Zied ANDOLSI
 */
public class ResourceAccessException extends Exception {

	/**
	 * {@link ResourceAccessException}'s default serial identifier.
	 */
	private static final long serialVersionUID = 1042871927947984548L;

	/**
	 * Constructs a new <code>ResourceAccessException</code>.It only calls the corresponding parent constructor.
	 */
	public ResourceAccessException() {
		super();
	}

	/**
	 * Constructs a new <code>ResourceAccessException</code> with the specified detail message. It only calls the
	 * corresponding parent constructor.
	 * 
	 * @param msg
	 *            message
	 */
	public ResourceAccessException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a new <code>ResourceAccessException</code> with the specified detail message and encapsulated
	 * exception. It only calls the corresponding parent constructor.
	 * 
	 * @param msg
	 *            message
	 * @param e
	 *            throwable
	 */
	public ResourceAccessException(String msg, Throwable e) {
		super(msg, e);
	}

	/**
	 * Constructs a new <code>ResourceAccessException</code> with the encapsulated exception. It only calls the
	 * corresponding parent constructor.
	 * 
	 * @param e
	 *            throwable
	 */
	public ResourceAccessException(Throwable e) {
		super(e);
	}

}
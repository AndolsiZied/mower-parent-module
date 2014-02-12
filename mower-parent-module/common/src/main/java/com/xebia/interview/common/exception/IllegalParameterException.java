package com.xebia.interview.common.exception;

/**
 * The {@link IllegalParameterException} exception should be thrown when the value of an argument does not respect the
 * excepted range of values. The conditions for the verification should be very simple (null, empty, negative...).
 * 
 * @author Zied ANDOLSI
 */
public class IllegalParameterException extends Exception {

	/**
	 * {@link IllegalParameterException}'s default serial identifier.
	 */
	private static final long serialVersionUID = 6636121422779321418L;

	/**
	 * Constructs a new <code>IllegalParameterException</code>.It only calls the corresponding parent constructor.
	 */
	public IllegalParameterException() {
		super();
	}

	/**
	 * Constructs a new <code>IllegalParameterException</code> with the specified detail message. It only calls the
	 * corresponding parent constructor.
	 * 
	 * @param msg
	 *            message
	 */
	public IllegalParameterException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a new <code>IllegalParameterException</code> with the specified detail message and encapsulated
	 * exception. It only calls the corresponding parent constructor.
	 * 
	 * @param msg
	 *            message
	 * @param e
	 *            throwable
	 */
	public IllegalParameterException(String msg, Throwable e) {
		super(msg, e);
	}

	/**
	 * Constructs a new <code>IllegalParameterException</code> with the encapsulated exception. It only calls the
	 * corresponding parent constructor.
	 * 
	 * @param e
	 *            throwable
	 */
	public IllegalParameterException(Throwable e) {
		super(e);
	}

}
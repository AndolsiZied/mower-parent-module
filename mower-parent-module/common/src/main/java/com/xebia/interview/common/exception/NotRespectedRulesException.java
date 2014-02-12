package com.xebia.interview.common.exception;

/**
 * The {@link NotRespectedRulesException} exception should be thrown when the state of an object (or a group of objects)
 * is not consistent.
 * 
 * @author Zied ANDOLSI
 */
public class NotRespectedRulesException extends Exception {

	/**
	 * {@link NotRespectedRulesException}'s default serial identifier.
	 */
	private static final long serialVersionUID = 1685727803132850334L;

	/**
	 * Constructs a new <code>NotRespectedRulesException</code>.It only calls the corresponding parent constructor.
	 */
	public NotRespectedRulesException() {
		super();
	}

	/**
	 * Constructs a new <code>NotRespectedRulesException</code> with the specified detail message. It only calls the
	 * corresponding parent constructor.
	 * 
	 * @param msg
	 *            message
	 */
	public NotRespectedRulesException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a new <code>NotRespectedRulesException</code> with the specified detail message and encapsulated
	 * exception. It only calls the corresponding parent constructor.
	 * 
	 * @param msg
	 *            message
	 * @param e
	 *            throwable
	 */
	public NotRespectedRulesException(String msg, Throwable e) {
		super(msg, e);
	}

	/**
	 * Constructs a new <code>NotRespectedRulesException</code> with the encapsulated exception. It only calls the
	 * corresponding parent constructor.
	 * 
	 * @param e
	 *            throwable
	 */
	public NotRespectedRulesException(Throwable e) {
		super(e);
	}

}
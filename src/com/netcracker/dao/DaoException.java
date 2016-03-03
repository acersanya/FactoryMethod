package com.netcracker.dao;

/**
 * 
 * @author acersanya
 *	Personal Exception class
 */
public class DaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public DaoException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DaoException(String msg) {
		super(msg);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException() {
		super();
	}

}

package com.retroduction.carma.application;

public class CarmaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9194127602632068930L;


	public CarmaException(String arg0) {
		super(arg0);

	}

	public CarmaException(Throwable arg0) {
		super(arg0);

	}

	public CarmaException(String arg0, Throwable arg1) {
		super(arg0, arg1);

	}

}

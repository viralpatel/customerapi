package io.customer.customerapi.services;

public class ServerException extends RuntimeException {

	private static final long serialVersionUID = -5257618716041591388L;

	public ServerException(Throwable e) {
		super(e);
	}

}

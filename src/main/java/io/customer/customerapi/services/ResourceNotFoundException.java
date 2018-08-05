package io.customer.customerapi.services;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2200629753392809173L;

	public ResourceNotFoundException(Throwable e) {
		super(e);
	}
}

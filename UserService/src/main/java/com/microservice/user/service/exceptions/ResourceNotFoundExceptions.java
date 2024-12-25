package com.microservice.user.service.exceptions;

public class ResourceNotFoundExceptions extends RuntimeException{
	
	public ResourceNotFoundExceptions() {
		super("Resource Not Found Exception!!");
	}
	
	public ResourceNotFoundExceptions(String message) {
		super(message);
	}
}

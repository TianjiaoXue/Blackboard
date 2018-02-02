package com.my.black.exception;

public class SAException extends Exception
{
	public SAException(String message)
	{
		super("SAException-"+ message);
	}
	
	public SAException(String message, Throwable cause)
	{
		super("SAException-"+ message,cause);
	}
}
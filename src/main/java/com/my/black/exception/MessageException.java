package com.my.black.exception;

public class MessageException  extends Exception
{
	public MessageException(String message)
	{
		super("Messageexception-"+ message);
	}
	
	public MessageException(String message, Throwable cause)
	{
		super("Messageexception-"+ message,cause);
	}
}


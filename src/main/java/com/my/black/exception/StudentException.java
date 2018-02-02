package com.my.black.exception;

public class StudentException extends Exception
{
	public StudentException(String message)
	{
		super("StudentException"+ message);
	}
	
	public StudentException(String message, Throwable cause)
	{
		super("StudentException"+ message,cause);
	}
}

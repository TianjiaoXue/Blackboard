package com.my.black.exception;

public class AssignmentException extends Exception
{
	public AssignmentException(String message)
	{
		super("AssignmentException-"+ message);
	}
	
	public AssignmentException(String message, Throwable cause)
	{
		super("AssignmentException-"+ message,cause);
	}
}

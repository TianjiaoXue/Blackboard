package com.my.black.exception;

public class ProfessorException extends Exception
{
	public ProfessorException(String message)
	{
		super("ProfessorException-"+ message);
	}
	
	public ProfessorException(String message, Throwable cause)
	{
		super("ProfessorException-"+ message,cause);
	}
}

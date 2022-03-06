package com.example.demo.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TooMuchShoesException extends RuntimeException
{
	public TooMuchShoesException(final String message) {
		super(message);
	}
}

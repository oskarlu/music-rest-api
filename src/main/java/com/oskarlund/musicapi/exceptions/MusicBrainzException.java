package com.oskarlund.musicapi.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class MusicBrainzException extends ResponseStatusException {

	public MusicBrainzException(HttpStatus httpStatus, String reason, Throwable cause) {
		super(httpStatus, reason, cause);
	}
}

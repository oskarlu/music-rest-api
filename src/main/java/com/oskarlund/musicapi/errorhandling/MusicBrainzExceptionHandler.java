package com.oskarlund.musicapi.errorhandling;


import com.oskarlund.musicapi.web.ResponseJson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class MusicBrainzExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { MusicBrainzException.class })
	protected ResponseEntity<Object> handleException(MusicBrainzException ex, WebRequest request) {

		ResponseJson.Error body = new ResponseJson.Error(ex.getMessage(), ex.getStatus().value());

		return handleExceptionInternal(ex, body,
			new HttpHeaders(), ex.getStatus(), request);
	}
}

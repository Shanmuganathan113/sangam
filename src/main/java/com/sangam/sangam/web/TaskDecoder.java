package com.sangam.sangam.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class TaskDecoder implements ErrorDecoder{
	
	@Override
	public ResponseStatusException decode(String methodKey, Response response) {
		if( response.status() >=400  && response.status() <= 499)
			return new ResponseStatusException(HttpStatus.valueOf(response.status()), "Requested resource not found");
		
		if( response.status() >=500  && response.status() <= 599)
			return new ResponseStatusException(HttpStatus.valueOf(response.status()), "Unknown Server error occured");
		return null;
	}
}

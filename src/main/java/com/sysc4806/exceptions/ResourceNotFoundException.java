package com.sysc4806.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by adambatson on 3/16/2017.
 * Use this class to throw 404 errors when the client requests
 * a resource that does not exist.  Simply add a :
 * throw new ResourceNotFoundException()
 * to trigger a 404
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class ResourceNotFoundException extends RuntimeException {
}

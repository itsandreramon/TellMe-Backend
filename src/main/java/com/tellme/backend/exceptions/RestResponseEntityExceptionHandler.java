/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.exceptions;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({
    TellNotFoundException.class,
    UserNotFoundException.class,
    AuthUserNotFoundException.class,
    UserListNotFoundException.class
  })
  public void springHandleNotFound(HttpServletResponse response) throws IOException {
    response.sendError(HttpStatus.NOT_FOUND.value());
  }

  @ExceptionHandler({InvalidDateException.class})
  public void springHandleInvalidDateFormat(HttpServletResponse response) throws IOException {
    response.sendError(HttpStatus.NOT_ACCEPTABLE.value());
  }

  @ExceptionHandler({
    UserNotUpdatedException.class,
    UserNotDeletedException.class,
    UserAlreadyExistsException.class
  })
  public void springHandleNotChanged(HttpServletResponse response) throws IOException {
    response.sendError(HttpStatus.CONFLICT.value());
  }
}

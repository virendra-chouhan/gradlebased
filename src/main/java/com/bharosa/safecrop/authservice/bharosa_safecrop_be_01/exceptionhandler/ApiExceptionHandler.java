package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.exceptionhandler;

import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.dto.ExceptionDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(IndexOutOfBoundsException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ExceptionDto indexoutofexception(IndexOutOfBoundsException ex) {

    return new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Array index out of bound excetion");

  }

  @ExceptionHandler(NullPointerException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ExceptionDto nullpointer(NullPointerException ex) {

    return new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Null pointer Exception excetion");

  }

  @ExceptionHandler(CustomeException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ExceptionDto customeException(CustomeException ex) {

    return new ExceptionDto(HttpStatus.BAD_REQUEST.value(), ex.getError());

  }

}

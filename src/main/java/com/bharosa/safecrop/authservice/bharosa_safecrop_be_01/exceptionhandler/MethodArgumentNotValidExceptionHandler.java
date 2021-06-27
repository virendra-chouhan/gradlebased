package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.helper.ValidationErrorMsgHelper;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice

public class MethodArgumentNotValidExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
		BindingResult result = ex.getBindingResult();
		List<ValidationErrorMsgHelper> listobj = new ArrayList<ValidationErrorMsgHelper>();
		List<String> errorList = new ArrayList<>();
		result.getFieldErrors().forEach((fieldError) -> {
			ValidationErrorMsgHelper obj = new ValidationErrorMsgHelper();
			obj.setField(fieldError.getField());
			obj.setMessage(fieldError.getDefaultMessage());
			listobj.add(obj);
		});

		return new Error(HttpStatus.BAD_REQUEST.value(), "Validation Failed", listobj);
	}

	public static class Error<T> {

		private int code;
		private String errormessage;
		private T errors;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getErrormessage() {
			return errormessage;
		}

		public void setErrormessage(String errorMessage) {
			this.errormessage = errorMessage;
		}

		public T getErrors() {
			return errors;
		}

		public void setErrors(T error) {
			this.errors = error;
		}

		public Error(int code, String errorMessage, T error) {
			super();
			this.code = code;
			this.errormessage = errorMessage;
			this.errors = error;
		}

	}
}

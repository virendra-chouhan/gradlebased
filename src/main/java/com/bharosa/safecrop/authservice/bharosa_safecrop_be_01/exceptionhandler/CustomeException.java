package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.exceptionhandler;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomeException extends Exception {

  private String error;

}

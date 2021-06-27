package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple domain object for our API to return a message.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String message;
    private int status;
	
}
package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
  private String user_id;
  private String mobile_no;

}

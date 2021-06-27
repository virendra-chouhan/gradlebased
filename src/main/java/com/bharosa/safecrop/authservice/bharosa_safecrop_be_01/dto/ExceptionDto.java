package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDto {
  private String timestamp = this.getTimeStamp();
  private int status;
  private String error;

  public ExceptionDto(int status, String error) {
    this.status = status;
    this.error = error;
  }

  public String getTimeStamp() {
    Timestamp systemtime = new Timestamp(System.currentTimeMillis());
    String timestamp = String.valueOf(systemtime);
    return timestamp;
  }
}

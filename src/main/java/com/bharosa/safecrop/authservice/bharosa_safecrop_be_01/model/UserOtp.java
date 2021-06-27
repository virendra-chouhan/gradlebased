package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_otp")
@ToString
public class UserOtp {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int user_otp_id;
  @NotBlank(message = "User Id Can not be empty")

  private String user_id;

  private int otp_value;
  private long otp_created_ts;
  private String otp_status;
}

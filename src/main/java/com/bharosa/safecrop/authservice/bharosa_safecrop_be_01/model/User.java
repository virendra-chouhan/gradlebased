package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User {

	@Id
	private String user_id;
	@NotBlank(message = "this filed is mandatory")
	@Size(min = 3, max = 12, message = "Please enter name between 3 and 30 character")
	private String user_name;
	@NotBlank(message = "enter a valid 10-digit mobile number")
	@Size(min = 10, max = 10, message = "enter a valid 10-digit mobile number")

	private String mobile_no;
	@NotBlank(message = "this filed is mandatory")
	private String tehsil_id;

	private String user_role_id;
	private String user_status;
	private String row_created_at;
	private String row_updated_at;
	private String date_of_registration;

}

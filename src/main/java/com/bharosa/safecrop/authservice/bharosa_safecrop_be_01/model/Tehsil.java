package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tehsils")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tehsil {
	@Id
	private String tehsil_id;
	private String district_id;

	private String tehsil_name;

}

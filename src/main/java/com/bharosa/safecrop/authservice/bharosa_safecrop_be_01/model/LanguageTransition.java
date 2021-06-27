package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "language_transition")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanguageTransition {
	@Id
	private int lt_id;
	private String language_code;
	private String translated_object_id;
	private String translated_value;

}

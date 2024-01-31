package com.customercrud.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long id;
	private String email;
	private String password;
}

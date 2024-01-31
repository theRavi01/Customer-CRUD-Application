package com.customercrud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Customer {

	@Id
	@GeneratedValue
	private Long id;
	private String firstName;
	private String lastName;
	private String street;
	private String address;
	private String city;
	private String state;
	private String email;
	private String phone;
}

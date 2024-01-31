package com.customercrud.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.customercrud.helper.Helper;
import com.customercrud.model.Customer;

@Repository
public interface CustomRepository{
	
	List<Customer> get(String firstName,String lastName,String email,String phone
			,String state, String street, String city ,String address,Helper helper);
	
	Long getPagination(String firstName,String lastName,String email,String phone
			,String state, String street, String city ,String address,Helper helper);

}

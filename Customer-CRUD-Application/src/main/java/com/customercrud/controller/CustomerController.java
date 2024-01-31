package com.customercrud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customercrud.exception.CustomerException;
import com.customercrud.helper.Helper;
import com.customercrud.model.Customer;
import com.customercrud.response.ResponseEntityObject;
import com.customercrud.service.CustomerRepositoryImpl;
import com.customercrud.service.CustomerService;

/*
@Author: Ravikant Pandey
*/

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerRepositoryImpl customerRepositoryImpl;

//	Create New Customer
	@PostMapping("/")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws CustomerException {
	Customer newCustomer =customerService.createCustomer(customer);
	return new ResponseEntity<Customer>(newCustomer,HttpStatus.OK);	
	}
	
//	Search Customer By Id
	@GetMapping("/searchByID/{id}")
	public ResponseEntity<Customer> findById(@PathVariable Long id)throws CustomerException {
	   
	 return new ResponseEntity<Customer>(customerService.customerFindById(id),HttpStatus.OK);
	}
	
//	Delete Customer By Id
	@DeleteMapping("/deleteCustomer/{id}")
	public String deleteCustomer(@PathVariable Long id)throws CustomerException{
		return customerService.deleteCustomer(id);
	}
	
//	Update Customer Details By Id
	@PutMapping("/updateCustomer/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer)throws CustomerException{
		Customer updatedCustomer = customerService.updateCustomerDetails(id, customer);
		return new ResponseEntity<Customer>(updatedCustomer,HttpStatus.OK);
	}
	
//	 Search Customer Details By Criteria Builder
	
	@GetMapping("/getCustomers")
	public ResponseEntityObject<List<Customer>> getCustomers(
			@RequestParam(value = "firstName", required = false,defaultValue = "") String firstName,
			@RequestParam(value = "lastName", required = false, defaultValue = "") String lastName,
			@RequestParam(value="email",required=false, defaultValue="")String email,
			@RequestParam(value = "phone", required = false,defaultValue = "") String phone,
			@RequestParam(value = "state", required = false,defaultValue = "") String state,
			@RequestParam(value = "street", required = false,defaultValue = "") String street,
			@RequestParam(value = "city", required = false,defaultValue = "") String city,
			@RequestParam(value = "address", required = false,defaultValue = "") String address,
			@RequestParam(value = "searchBy", required = false,defaultValue = "") String searchBy,
			@RequestParam(value = "search", required = false,defaultValue = "") String search,
			@RequestParam(value = "sortOrder", required = false,defaultValue = "") String sortOrder,
			@RequestParam(value = "sortBy", required = false,defaultValue = "") String sortBy,
			@RequestParam(value = "currentPage", required = false,defaultValue = "0") int currentPage,
			@RequestParam(value = "itemsPerPage", required = false,defaultValue = "0") int itemsPerPage) {

		Helper help=new Helper();
		help.setSearch(search);
		help.setSearchBy(searchBy);
		help.setSortOrder(sortOrder);
		help.setSortBy(sortBy);
		help.setCurrentPage(currentPage);
		help.setItemsPerPage(itemsPerPage);

	Long count= customerRepositoryImpl.getPagination( firstName, lastName, email, phone
			, state,  street,  city , address, help);
	 List<Customer> customer=customerRepositoryImpl.get(firstName, lastName, email, phone
				, state,  street,  city , address, help);
	 ResponseEntityObject<List<Customer>> liCustomer= new 
			ResponseEntityObject<List<Customer>>(false,"TotalItemsFind", customer, count);
	 
	 return liCustomer;
	}
}

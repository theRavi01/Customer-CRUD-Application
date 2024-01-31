package com.customercrud.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.customercrud.exception.CustomerException;
import com.customercrud.model.Customer;
import com.customercrud.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
//	create new Customer 
	public Customer createCustomer(Customer customer)throws CustomerException {
		if(customerRepository.findByEmail(customer.getEmail())!=null) {
			throw new CustomerException("Customer Already exist...");
		}
		if(customer.getAddress()==null || customer.getAddress()=="" ||
			customer.getCity()==null || customer.getCity()=="" ||
			customer.getEmail()==null || customer.getEmail()=="" ||
			customer.getFirstName()==null || customer.getFirstName()=="" ||
			customer.getLastName()==null || customer.getLastName()=="" ||
			customer.getPhone()==null || customer.getPhone()=="" ||
			customer.getState()==null || customer.getState()=="" ||
			customer.getStreet()==null || customer.getStreet()=="") {
			throw new CustomerException("Customer details are empty like- email,phone,firstName,lastName etc");
		}
		return customerRepository.save(customer);
	}
	
	
//	Fetch customer by id
	public Customer customerFindById(Long id) throws CustomerException{
		Customer customer= customerRepository.findById(id).orElse(null);
		if(customer==null) {
			throw new CustomerException("Customer not found...");
		}
		return customer;
	}
	

	
// Delete Customer by id	
	public String deleteCustomer(Long id)throws CustomerException{
       Customer customer= customerRepository.findById(id).orElse(null);
	   if(customer==null) {
		throw new CustomerException("Customer not exist..");
	   }
	  System.out.println( customer.getEmail());
	   customerRepository.deleteById(id);
	   return "Customer Deleted Successfully..";
	}
	
//	Update Customer Details by customer id 
	public Customer updateCustomerDetails(Long id,Customer customer)throws CustomerException {
		Customer existCustomer = customerRepository.findById(id).orElse(null);
		if(existCustomer==null) {
			throw new CustomerException("Customer not exist...");
		}
		Customer updateCustomer =new Customer();
		updateCustomer.setId(id);
		updateCustomer.setAddress(customer.getAddress());
		updateCustomer.setCity(customer.getCity());
		updateCustomer.setEmail(customer.getEmail());
		updateCustomer.setFirstName(customer.getFirstName());
		updateCustomer.setLastName(customer.getLastName());
		updateCustomer .setPhone(customer.getPhone());
		updateCustomer.setState(customer.getState());
		updateCustomer.setStreet(customer.getStreet());
		return customerRepository.save(updateCustomer);
	}
}

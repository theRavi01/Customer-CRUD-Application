package com.customercrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customercrud.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{

public Customer findByEmail(String email);

}

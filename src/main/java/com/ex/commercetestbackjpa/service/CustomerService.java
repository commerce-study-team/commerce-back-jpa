package com.ex.commercetestbackjpa.service;

import com.ex.commercetestbackjpa.domain.entity.Customer;
import com.ex.commercetestbackjpa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public void saveCustomer(Customer customer) {

        customer.setUserId("testid");
        customer.setUserPw("testpw");
        customer.setUserName("testname");

        customerRepository.save(customer);
    }
}

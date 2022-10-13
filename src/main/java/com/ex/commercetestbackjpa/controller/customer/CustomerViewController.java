package com.ex.commercetestbackjpa.controller.customer;

import com.ex.commercetestbackjpa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/cust")
public class CustomerViewController {

    @Autowired
    private CustomerService customerService;

    @PostMapping()
    public void saveCustomer(){

    }
}

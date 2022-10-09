package com.ex.commercetestbackjpa;

import com.ex.commercetestbackjpa.domain.entity.Customer;
import com.ex.commercetestbackjpa.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void testClass(){
        Customer customer = new Customer();

        customer.setUserId("id001");
        customer.setUserPw("pw001");
        customer.setUserName("name001");

        customerRepository.save(customer);
    }
}

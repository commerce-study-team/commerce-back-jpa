package com.ex.commercetestbackjpa;

import com.ex.commercetestbackjpa.domain.entity.customer.Customer;
import com.ex.commercetestbackjpa.domain.entity.customer.dto.CustomerRequestDto;
import com.ex.commercetestbackjpa.domain.entity.customer.dto.CustomerResponseDto;
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
        CustomerRequestDto customerRequestDto = new CustomerRequestDto();

//        customerRequestDto.setEmail("shnam@cware.co.kr");
//        customerRequestDto.setName("남시훈");
        customerRequestDto.setEmail("jbae@cware.co.kr");
        customerRequestDto.setName("배준");
        customerRequestDto.setPassword("1q2w34r!");

        customerRepository.save(customerRequestDto.toEntity());
    }
}

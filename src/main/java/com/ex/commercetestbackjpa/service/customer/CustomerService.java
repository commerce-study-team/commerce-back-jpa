package com.ex.commercetestbackjpa.service.customer;

import com.ex.commercetestbackjpa.domain.entity.customer.Customer;
import com.ex.commercetestbackjpa.domain.dto.customer.CustomerRequestDto;
import com.ex.commercetestbackjpa.domain.dto.customer.CustomerResponseDto;
import com.ex.commercetestbackjpa.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public Long saveCustomer(@Valid CustomerRequestDto customerRequestDto) {
        return customerRepository.save(customerRequestDto.toEntity()).getCustNo();
    }

    public HashMap<String, Object> findCustomerAll() {
        HashMap<String, Object> returnMap = new HashMap<>();

        List<CustomerResponseDto> customerResDtos = customerRepository.findAll().stream()
                                                                                .map(CustomerResponseDto::new)
                                                                                .collect(Collectors.toList());
        returnMap.put("RESULT", customerResDtos);
//        returnMap.put("PAGE_INFO", null); // 나중에 페이징 관련해서 화면단에 넘겨줄 데이터도 넣기

        return returnMap;
    }

    public CustomerResponseDto findCustomerByCustNo(Long custNo) {
        Customer customer = customerRepository.findById(custNo).orElseThrow(NoSuchElementException::new);

        return new CustomerResponseDto(customer);
    }

    @Transactional
    public Long updatCustomerByCustNo(Long custNo, CustomerRequestDto customerReqDto) {
        Customer customer = customerRepository.findById(custNo).orElseThrow(NoSuchElementException::new);

        // 위에서 없는 경우 Exception 처리 했으니 무조건 있다는 가정하에 수정쿼리 날리고 고객번호 리턴
        customer.update(customerReqDto.getName(), customerReqDto.getPassword());

        return custNo;
    }

    @Transactional
    public void deleteCustomerByCustNo(Long custNo) {
        Customer customer = customerRepository.findById(custNo).orElseThrow(NoSuchElementException::new);
        customer.delete(); // 실제 디비 삭제하지않고 사용여부 컬럼만 업데이트
    }
}

package com.ex.commercetestbackjpa.controller.customer;

import com.ex.commercetestbackjpa.domain.entity.customer.dto.CustomerRequestDto;
import com.ex.commercetestbackjpa.domain.entity.customer.dto.CustomerResponseDto;
import com.ex.commercetestbackjpa.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor // https://computer-science-student.tistory.com/622 << 한번 훑어보고 나중에 제거
public class CustomerApiController {

    private final CustomerService customerService;

    @PostMapping("")
    public Long saveCustomer(@RequestBody @Valid CustomerRequestDto customerReqDto){ // @Valid https://mangkyu.tistory.com/174 << 한번 훑어보고 나중에 제거
        return customerService.saveCustomer(customerReqDto);
    }

    @GetMapping("")
    public HashMap<String, Object> findCustomerAll(){
        return customerService.findCustomerAll();
    }

    @GetMapping("/{custNo}")
    public CustomerResponseDto findCustomerByCustNo(@PathVariable Long custNo){
        return customerService.findCustomerByCustNo(custNo);
    }

    @PutMapping("/{custNo}")
    public Long updateCustomerByBoardId(@PathVariable Long custNo, @RequestBody @Valid CustomerRequestDto customerReqDto) {
        return customerService.updatCustomerByCustNo(custNo, customerReqDto);
    }

    @DeleteMapping("/{custNo}")
    public void deleteCustomerByBoardId(@PathVariable Long custNo){
        customerService.deleteCustomerByCustNo(custNo);
    }


}

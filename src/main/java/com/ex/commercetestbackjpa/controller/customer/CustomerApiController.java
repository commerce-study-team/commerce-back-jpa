package com.ex.commercetestbackjpa.controller.customer;

import com.ex.commercetestbackjpa.domain.dto.customer.CustomerRequestDto;
import com.ex.commercetestbackjpa.domain.dto.customer.CustomerResponseDto;
import com.ex.commercetestbackjpa.service.customer.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@Api(tags = {"고객 Controller"})
@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor // https://computer-science-student.tistory.com/622 << 한번 훑어보고 나중에 제거
public class CustomerApiController {

    private final CustomerService customerService;

    @ApiOperation(value = "고객 저장")
    @PostMapping("")
    public Long saveCustomer(@RequestBody @Valid CustomerRequestDto customerReqDto){ // @Valid https://mangkyu.tistory.com/174 << 한번 훑어보고 나중에 제거
        return customerService.saveCustomer(customerReqDto);
    }

    @ApiOperation(value = "고객 리스트 조회")
    @GetMapping("")
    public HashMap<String, Object> findCustomerAll(){
        return customerService.findCustomerAll();
    }

    @ApiOperation(value = "고객 조회")
    @GetMapping("/{custNo}")
    public CustomerResponseDto findCustomerByCustNo(@PathVariable Long custNo){
        return customerService.findCustomerByCustNo(custNo);
    }

    @ApiOperation(value = "고객 수정")
    @PatchMapping("/{custNo}")
    public Long updateCustomerByBoardId(@PathVariable Long custNo, @RequestBody @Valid CustomerRequestDto customerReqDto) {
        return customerService.updatCustomerByCustNo(custNo, customerReqDto);
    }

    @ApiOperation(value = "고객 삭제")
    @DeleteMapping("/{custNo}")
    public void deleteCustomerByBoardId(@PathVariable Long custNo){
        customerService.deleteCustomerByCustNo(custNo);
    }


}

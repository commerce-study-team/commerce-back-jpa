package com.ex.commercetestbackjpa.controller.order;

import com.ex.commercetestbackjpa.domain.dto.customer.CustomerRequestDto;
import com.ex.commercetestbackjpa.domain.dto.customer.CustomerResponseDto;
import com.ex.commercetestbackjpa.domain.dto.order.OrderDtDto;
import com.ex.commercetestbackjpa.service.customer.CustomerService;
import com.ex.commercetestbackjpa.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@Api(tags = {"주문 Controller"})
@RestController
@RequestMapping("/api/ord")
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @ApiOperation(value = "주문 리스트 조회")
    @GetMapping("")
    public HashMap<String, Object> findOrderAll(){
        return orderService.findOrderAll();
    }

    @ApiOperation(value = "주문 조회")
    @GetMapping("/{ordNo}")
    public OrderDtDto.Response findOrderByOrdNo(@PathVariable Long ordNo){
        return orderService.findOrderByOrdNo(ordNo);
    }

    @ApiOperation(value = "주문 저장")
    @PostMapping("")
    public Long saveOrder(@RequestBody @Valid OrderDtDto.Request OrderDtReqDto){
        return orderService.saveOrder(OrderDtReqDto);
    }
}

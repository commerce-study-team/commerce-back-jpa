package com.ex.commercetestbackjpa.service.order;

import com.ex.commercetestbackjpa.domain.dto.customer.CustomerResponseDto;
import com.ex.commercetestbackjpa.domain.dto.order.OrderDtDto;
import com.ex.commercetestbackjpa.domain.dto.product.ProductPriceDTO;
import com.ex.commercetestbackjpa.domain.entity.order.OrderDt;
import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    public HashMap<String, Object> findOrderAll() {
        HashMap<String, Object> returnMap = new HashMap<>();

        List<OrderDtDto.Response> orderDtDtos = orderRepository.findAll().stream()
                .map(OrderDtDto.Response::new)
                .collect(Collectors.toList());
        returnMap.put("RESULT", orderDtDtos);
//        returnMap.put("PAGE_INFO", null); // 나중에 페이징 관련해서 화면단에 넘겨줄 데이터도 넣기

        return returnMap;
    }

    public OrderDtDto.Response findOrderByOrdNo(Long ordNo) {
        OrderDt orderDt = orderRepository.findById(ordNo).orElseThrow(NoSuchElementException::new);

        return new OrderDtDto.Response(orderDt);
    }

    @Transactional
    public Long saveOrder(OrderDtDto.Request orderDtReqDto) {
        OrderDt orderDt = orderRepository.findById(orderDtReqDto.getOrdNo()).orElseThrow(() -> new NoSuchElementException("주문 정보를 찾을 수 없습니다."));
        orderRepository.save(orderDtReqDto.toEntity(orderDt));

        return orderDt.getOrdNo();
    }
}

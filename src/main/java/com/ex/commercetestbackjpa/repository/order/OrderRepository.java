package com.ex.commercetestbackjpa.repository.order;

import com.ex.commercetestbackjpa.domain.entity.customer.Customer;
import com.ex.commercetestbackjpa.domain.entity.order.OrderDt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderDt, Long> {
}

package com.ex.commercetestbackjpa.repository.customer;

import com.ex.commercetestbackjpa.domain.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

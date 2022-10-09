package com.ex.commercetestbackjpa.repository;

import com.ex.commercetestbackjpa.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

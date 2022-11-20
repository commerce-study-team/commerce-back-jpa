package com.ex.commercetestbackjpa.domain.dto.customer;

import com.ex.commercetestbackjpa.domain.entity.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerResponseDto {
    private Long custNo;
    private String email;
    private String name;

    public CustomerResponseDto(Customer customer) {
        this.custNo = customer.getCustNo();
        this.email = customer.getEmail();
        this.name = customer.getName();
    }
}

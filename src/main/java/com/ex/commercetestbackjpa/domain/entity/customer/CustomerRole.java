package com.ex.commercetestbackjpa.domain.entity.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomerRole {

    C00("ADMIN"),
    CO1("USER");

    private final String role;
}

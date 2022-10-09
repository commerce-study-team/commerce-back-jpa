package com.ex.commercetestbackjpa.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name="tcustomer")
@Getter @Setter
@ToString
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userName;
    private String userId;
    private String userPw;

    // CreatedBy, CreatedDate, LastModifiedBy, LastModifiedDate 추후에 추가
}
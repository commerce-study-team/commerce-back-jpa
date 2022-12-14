package com.ex.commercetestbackjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class CommerceTestBackJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommerceTestBackJpaApplication.class, args);
    }
}

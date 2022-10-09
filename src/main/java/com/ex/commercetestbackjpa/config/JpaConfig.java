package com.ex.commercetestbackjpa.config;

import com.ex.commercetestbackjpa.domain.entity.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

import javax.annotation.Resource;
import java.util.Optional;

//@Configuration
//@EnableJpaAuditing
public class JpaConfig {

    @Resource
    private Customer customer;

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new AuditorAware<>() {
            @Override
            public Optional<Long> getCurrentAuditor() {
                Long userId = customer.getId();
                return Optional.of(userId);
            }

        };
    }
}
package com.ex.commercetestbackjpa.config.auth.jwt;

import com.ex.commercetestbackjpa.domain.entity.customer.Customer;
import com.ex.commercetestbackjpa.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationProvider {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Customer customer = customerRepository.findByCustomerId(authentication.getPrincipal().toString());

        if (encoder.matches(authentication.getCredentials().toString(), customer.getPassword())) {
        } else {
            throw new BadCredentialsException("비밀번호 오류입니다.");
        }

        AuthenticationToken token = new AuthenticationToken(customer.getCustomerId(), customer.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        token.setId(customer.getCustNo());
        token.setUserId(customer.getCustomerId());
        token.setRole(customer.getCustomerRole().getRole());

        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AuthenticationToken.class);
    }
}

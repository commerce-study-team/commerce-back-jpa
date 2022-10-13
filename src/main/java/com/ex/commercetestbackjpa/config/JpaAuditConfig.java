package com.ex.commercetestbackjpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Configuration
public class JpaAuditConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String userId = request.getHeader("uid"); // 로그인 방식에 따라 변경해줘야함 일단은 헤더정보가지고 테스트만 해보기
        return Optional.of(userId);
    }
}
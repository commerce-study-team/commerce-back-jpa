package com.ex.commercetestbackjpa.config.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class JwtAuthToken implements AuthToken<Claims>{

    private final String token;
    private final Key key;

    public JwtAuthToken(String token, Key key) {
        this.token = token;
        this.key = key;
    }

    public JwtAuthToken(String id, Key key, String role, Map<String, String> claims, Date expireDate) {
        this.key = key;
        this.token = this.createJwtAuthToken(id, role, claims, expireDate).get();
    }

    public Optional<String> createJwtAuthToken(String id, String role, Map<String, String> claimsMap, Date expireDate) {

        Claims claims = new DefaultClaims(claimsMap);
        claims.put(JwtAuthToken.AUTHORITIES_KEY, role);

        return Optional.ofNullable(Jwts.builder()
                .setSubject(id)
                .addClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expireDate)
                .compact()
        );
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean validate() {
        return getDate() != null;
    }

    @Override
    public Claims getDate() {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody();
        } catch (SecurityException e) {
            log.debug(e.toString());
        } catch (MalformedJwtException e) {
            log.debug(e.toString());
        } catch (ExpiredJwtException e) {
            log.debug(e.toString());
        } catch (UnsupportedJwtException e) {
            log.debug(e.toString());
        } catch (IllegalArgumentException e) {
            log.debug(e.toString());
        }
        return null;
    }
}

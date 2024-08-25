package com.euvatar.euvatarapi.config.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMillis;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtTokenProvider(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        Instant now = Instant.now();

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusMillis(jwtExpirationInMillis))
                .subject(userPrincipal.getUsername())
                .claim("scope", scope)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
    }

    public String getUsernameFromToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        Jwt jwt = jwtDecoder.decode(token);
        final String username = jwt.getSubject();
        return (username.equals(userDetails.getUsername()) && !Objects.requireNonNull(jwt.getExpiresAt()).isBefore(Instant.now()));
    }
}
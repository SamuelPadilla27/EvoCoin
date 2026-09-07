package com.crypto.EvoCoin.auth.service;

import com.crypto.EvoCoin.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {
    private final JwtEncoder jwtEncoder;
    private final long jwtExpiration;

    public JwtService(
            JwtEncoder jwtEncoder,
            @Value("${jwt.expiration}") long jwtExpiration
    ) {
        this.jwtEncoder = jwtEncoder;
        this.jwtExpiration = jwtExpiration;
    }

    public String generateToken(User pUser){
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(pUser.getUsername())
                .claim("userId", pUser.getId())
                .claim("role", pUser.getRole().name())
                .issuedAt(now)
                .expiresAt(now.plusMillis(jwtExpiration))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}

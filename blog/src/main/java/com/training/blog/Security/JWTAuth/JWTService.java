package com.training.blog.Security.JWTAuth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTService {
    @Value("${application.security.jwt.secret}")
    private String secret;
    @Value("${application.security.jwt.expiration}")
    private Long expiration;
    public String generateToken(UserDetails userDetail){
        if(userDetail==null) return "";
        else {
            Date date = new Date(System.currentTimeMillis());
            Date expirationDate = new Date(date.getTime() + expiration);
            Map<String, Object> claims = new HashMap<>();
            claims.put("email", userDetail.getUsername());
            var roles = userDetail.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority).toList();
            claims.put("roles",roles);
            return Jwts.builder()
                    .claims(claims)
                    .subject(userDetail.getUsername())
                    .issuedAt(date)
                    .expiration(expirationDate)
                    .signWith(getSecrectKey())
                    .compact();
        }
    }

    public String extractEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
     Claims claims = extractAllClaims(token);
     return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        Claims claims;
        SecretKey secretKey = (SecretKey) getSecrectKey();
        try {
           claims = Jwts.parser()
                   .verifyWith(secretKey)
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
        }catch (Exception e){
                claims = null;
        }
        return claims;
    }

    public boolean isValidToken(String token, UserDetails userDetails){
        String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Key getSecrectKey(){
        byte[] bytes = Decoders.BASE64URL.decode(this.secret);
        return Keys.hmacShaKeyFor(bytes);
    }
}

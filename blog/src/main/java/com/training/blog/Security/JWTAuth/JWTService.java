package com.training.blog.Security.JWTAuth;

import com.training.blog.Entities.Users;
import com.training.blog.Repositories.InvalidTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class JWTService {
    @Value("${application.security.jwt.secret}")
    private String secret;
    @Value("${application.security.jwt.expiration-token}")
    private Long expiration;
    @Value("${application.security.jwt.expiration-refresh-token}")
    private Long refreshTokenExp;
    private final InvalidTokenRepository invalidTokenRepository;

    public String generateToken(UserDetails userDetail){
        if(userDetail==null) return "";
        else {
            Date expirationDate =
                    new Date(Instant.now().plus(expiration, ChronoUnit.HOURS).toEpochMilli());
            var roles = userDetail.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority).toList();
            return Jwts.builder()
                    .setSubject(userDetail.getUsername()) //username but it's email
                    .setIssuer("training.blog.com")
                    .setIssuedAt(new Date())
                    .setExpiration(expirationDate)
                    .setId(UUID.randomUUID().toString())
                    .claim("scope", roles)
                    .signWith(getSecrectKey())
                    .compact();
        }
    }

    public String extractEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public String extractId(String token) {
        return extractClaims(token, Claims::getId);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
     Claims claims = extractAllClaims(token);
     return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        Claims claims;
        SecretKey secretKey = (SecretKey) getSecrectKey();
        try {
           claims = Jwts.parserBuilder()
                   .setSigningKey(secretKey)
                   .build()
                   .parseClaimsJwt(token)
                   .getBody();
           if(invalidTokenRepository.findById(claims.getId()).isPresent()){
               throw new Exception("Invalid token");
           }
        }catch (Exception e){
                claims = null;
        }
        return claims;
    }

    public boolean isValidToken(String token){
        return extractAllClaims(token)!=null && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Key getSecrectKey(){
        byte[] bytes = Decoders.BASE64URL.decode(this.secret);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String generateRefreshToken(Users user) {
        Date expirationDate = new Date(Instant.now()
                            .plus(refreshTokenExp, ChronoUnit.HOURS).toEpochMilli());
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuer("training.blog.com")
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .setId(UUID.randomUUID().toString())
                .signWith(getSecrectKey())
                .compact();
    }
}

//package com.example.employee_management_system.service;
//
//
//import com.example.employee_management_system.model.User;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//import java.util.function.Function;
//
//@Service
//public class JwtService {
//
//    private final String SECRET_KEY = "9544be24250a3ebce99c281de825d331159422075246d2c819d786cbaf8415b0";
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public boolean isValid(String token, CustomUserDetailsService user) {
//        String username = extractUsername(token);
//        return (username.equals(user.getUsername())) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
//        Claims claims = extractAllClaims(token);
//        return resolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser().verifyWith(getSigninKey()).build().parseSignedClaims(token).getPayload();
//    }
//
//    public String generateToken(String username) {
//
//        Map<String, Objects> claims = new HashMap<>();
//
//        return Jwts.builder().claims().add(claims).
//    }
//
//    private SecretKey getSigninKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//}

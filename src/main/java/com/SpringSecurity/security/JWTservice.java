package com.SpringSecurity.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.security.Key;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTservice {

    private String secretKey = "akjshf87234shfasdlkjh8234hfsdlkhsdf";

    private Key getSignKey() {
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        byte[] keyBytes = encodedKey.getBytes(); // You can also Base64 decode it if you encoded
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        System.out.println("generate token method calling" + userDetails.getUsername());
        Map<String , Object> claims = new HashMap<>();
        claims.put("roles" , userDetails.getAuthorities());
        return Jwts.builder()
                .setClaims(claims)                       // Custom claims (roles, id, etc.)
                .setSubject(userDetails.getUsername())                   // The username (main identity)
                .setIssuedAt(new Date(System.currentTimeMillis()))                // Token creation time
                .setExpiration(new Date(System.currentTimeMillis() +10*60*60*10)) // Expiry
                .signWith(getSignKey() ,SignatureAlgorithm.HS256)// Signing algorithm + key
                .compact();


    }

    public String extractUsername(String token) {

        return extractclaims(token , Claims::getSubject);
    }

    public <T> T extractclaims(String token , Function<Claims, T> claimResolver ) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractclaims(token , Claims::getExpiration);
    }
}

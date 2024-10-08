package com.todolist.app.util;

import com.todolist.app.entity.User;
import com.todolist.app.entity.UserCustomDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper{
    // token's expiration time
    private static final long tokenValidity = 3600000;
    // payload encryption
    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Retrieves username
    public String getUsernameFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Retrieves Expiration date of token
    public Date getExpirationDateFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    // Retrieves Claims from token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsRsolver)
    {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsRsolver.apply(claims);
    }

    // Returns all claims from token
    private Claims getAllClaimsFromToken(String token)
    {
        return Jwts.parserBuilder().setSigningKey(jwtSecret)
                .build().parseClaimsJws(token).getBody();
    }

    // checks if token is expired or not
    public Boolean isTokenExpired(String token)
    {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Generates token
    public String generateToken(UserDetails userDetails)
    {
         Map<String, Object> claims = new HashMap<>();
         claims.put("msg", "It's a useless token nothing to see here, shuu!!");
         if(userDetails instanceof UserCustomDetails customDetails)
         {
             User user = customDetails.getUser();
             return doGenerationToken(claims, userDetails.getUsername(), user);
         }else {
             throw new IllegalArgumentException("Unsupported UserDetails implementation");
         }
    }

    // Sets all clains and data in token
    private String doGenerationToken(Map<String, Object> claims, String subject, User user)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setId(user.getId().toString())
                .setIssuer("ToDo App")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
                .signWith(jwtSecret)
                .compact();
    }

    // Validates the token
    public Boolean validateToken(String token, UserDetails userDetails)
    {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Extracting ID from token
    public String extractId(String token)
    {
       String id = getClaimFromToken(token.substring(7), Claims::getId);
        return id;
    }
}

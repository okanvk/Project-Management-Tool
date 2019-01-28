package com.vukat.pmtool.security;

import com.vukat.pmtool.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static  com.vukat.pmtool.security.SecurityConstants.EXPIRATION_TIME;
import static  com.vukat.pmtool.security.SecurityConstants.SECRET;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtTokenProvider {


    //Generate Token

    public String generateToken(Authentication authentication){

        User user = (User) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());

        Date expire_date = new Date(now.getTime()+EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String,Object> claims = new HashMap<>();
        claims.put("id",(Long.toString(user.getId())));
        claims.put("username",user.getUsername());
        claims.put("fullName",user.getFullName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expire_date)
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .compact();


    }

    //Validate Token
    public boolean validateToken(String token){

        try{

            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;

        }catch(SignatureException ex) {
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            System.out.println("Invalid JWT Token");
        } catch (ExpiredJwtException e){
            System.out.println("Expired JWT Token");
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported JWT Token");
        }catch(IllegalArgumentException ex){
            System.out.println("JWT Claims string empty");
        }
        return false;
    }

    public Long getUserId(String token){

        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();

        String id = (String) claims.get("id");

        return Long.parseLong(id);
    }



    //Get User Id from the token

}

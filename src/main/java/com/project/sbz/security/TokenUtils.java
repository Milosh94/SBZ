package com.project.sbz.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils {

	@Value("myXAuthSecret")
	private String secret;
	
	@Value("18000")
	private Long expiration;
	
	public String getUsernameFromToken(String token){
		String username;
		
		try{
			Claims claims = this.getClaimsFromToken(token);
			username = claims.getSubject();
		}catch(Exception e){
			username = null;
		}
		return username;
	}
	
	public Claims getClaimsFromToken(String token){
		Claims claims;
		try{
			//System.out.println("token");
			//System.out.println(token);
			//System.out.println(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).toString());
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		}catch(Exception e){
			//System.out.println(e);
			claims = null;
		}
		//System.out.println(claims.entrySet());
		return claims;
	}
	
	public Date getExpirationDateFromToken(String token){
		Date expiration;
		try{
			Claims claims = this.getClaimsFromToken(token);
			expiration = claims.getExpiration();
		}catch(Exception e){
			expiration = null;
		}
		return expiration;
	}
	
	public boolean isTokenExpired(String token){
		Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public boolean validateToken(String token, UserDetails userDetails){
		String username = userDetails.getUsername();
		//System.out.println("validatetoken");
		//System.out.println(token);
		//System.out.println(this.getClaimsFromToken(token).isEmpty());
		//System.out.println(this.isTokenExpired(token));
		return this.getClaimsFromToken(token).getSubject().equals(username) && !this.isTokenExpired(token);
	}
	
	public String generateToken(UserDetails userDetails){
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("sub", userDetails.getUsername());
		claims.put("role", userDetails.getAuthorities());
		claims.put("created", new Date());
		return Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
}

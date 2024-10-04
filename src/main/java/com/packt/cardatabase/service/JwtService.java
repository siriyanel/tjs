package com.packt.cardatabase.service;

import java.security.Key;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	static final long EXPIRATIONTIME=86400000;
	static final String PREFIX = "Bearer";
	//비밀키 생성, HS256(Hash Algorithm) 256비트로 암호화함(암호와 알고리즘)
	static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	//서명된 JWT 토큰 생성(사용자, 암호화, 1일을 초로환산한 값)
	public String getToken(String username) {
		String token = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis()
						+ EXPIRATIONTIME))
				.signWith(key)
				.compact();	
		
		return token;  //JwtService.getToken()호출하면 토큰을 받는다.
	}
	
	public String getAuthUser(HttpServletRequest request) {
		//AUTHORIZATION 상수에 토큰을 받아옵니다.
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		//토큰 신호가 있을 경우 참이므로 if 실행영역을 실행합니다.
		if(token != null) {  
			String user = Jwts.parserBuilder()
					.setSigningKey(key)  //signingKey 필드에 key값을 저장함.
					.build()			//조립한다라는 의미는 데이터 저장을 완성함
					 //Jws분석을 요구함, replace()메소드는 PREFIX를 ""로 대처함
					.parseClaimsJws(token.replace(PREFIX, "")) 
					.getBody()      //내용을 받아옵니다.
					.getSubject();  //주제(username)를 받아옵니다.
			
			if(user != null) 
				return user;
			}
			return null;
	}
}



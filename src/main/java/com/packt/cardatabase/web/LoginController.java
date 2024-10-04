package com.packt.cardatabase.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cardatabase.domain.AccountCredentials;
import com.packt.cardatabase.service.JwtService;

@RestController
public class LoginController {
	//주입
	@Autowired  
	private JwtService jwtService;
	
	//주입
	@Autowired  
	AuthenticationManager authenticationManager;
	
    //	요청하는 그림은 값을 /login으로 하고, 방법은 post로 합니다.
	@RequestMapping(value="/login", method=RequestMethod.POST)
	//@RequestBody은 json형식의 body로 데이터가 입력되면 필드에 저장합니다.
	public ResponseEntity<?> getToken(@RequestBody 
			AccountCredentials credentials) {
		UsernamePasswordAuthenticationToken creds =
				new UsernamePasswordAuthenticationToken(
						
					//AccountCredentials클래스에서 정보를 가져옵니다.	
						credentials.getUsername(),											
						credentials.getPassword());
	
		//creds 변수의 내용을 authenticate()메소드가 진짜임을 증명합니다.
		Authentication auth = authenticationManager.authenticate(creds);
		
		//getToken()를 실행하면 사용자 정보를 갖는 토큰 생성(사용자 정보를 매개값을 입력)
		String jwts = jwtService.getToken(auth.getName());
				
		return ResponseEntity.ok()
				 //상수 AUTHORIZATION에 "Bearer " + jwts 데이터를 저장합니다.
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
				 //상수 ACCESS_CONTROL_EXPOSE_HEADERS에 "Authorization"를 저장합니다.
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
				.build();
	}
	

}

package com.packt.cardatabase;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, 
						HttpServletResponse response,
						AuthenticationException authException)
						throws IOException, ServletException {
		//상수에는 401 Unauthorized 메시지를 저장하고 예외가 발생하면 출력합니다.
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		//매체 형식을 JSON 형식으로 설정합니다.
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		PrintWriter writer = response.getWriter();
		
		//에러 내용을 화면에 출력합니다. 출력할 내용은 authException변수에 저장된 주소 메모리에 저장되어 있습니다. 
		writer.println("Error: " + authException.getMessage());
    	}		

}

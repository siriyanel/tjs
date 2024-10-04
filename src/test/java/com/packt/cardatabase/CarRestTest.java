package com.packt.cardatabase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class CarRestTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testAuthentication() throws Exception {
		this.mockMvc.
		perform(post("/login").
				content("{\"username\":\"admin\",\"password\":\"admin\"}").
				header(HttpHeaders.CONTENT_TYPE,"application/json")).
		//마지막행에서 실행한다. andExpect( )메소드는 상태를 요구합니다.
		//isOk() 메소는 응답 코드가 정상적인 처리 200인지 확인
				andDo(print()).andExpect(status().isOk());
	}
	

}

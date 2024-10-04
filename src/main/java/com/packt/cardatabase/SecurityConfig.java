package com.packt.cardatabase;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.packt.cardatabase.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private AuthenticationFilter authenticationFilter;
	
	@Autowired
	private AuthEntryPoint exceptionHandler;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
//	@Bean
//	@Override
//	protected UserDetailsService userDetailsService() {
//		UserDetails user = 
//				User.withDefaultPasswordEncoder().username("user")
//				.password("password").roles("USER").build();
//		
//		return super.userDetailsService();
//	}
	
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception{
		
		return authenticationManager();
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.sessionManagement()
		//세션 생성 정책을 비상태유지로 설정함.
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		//권한 부여를 아래와 같이 합니다.
		.authorizeRequests()
		//login 엔드포인트에 대한 POST 요청은 모두 허용 
		//antMatchers() 메소드는 리소스의 접근을 인증절차 없이 허용한다는 의미
		.antMatchers(HttpMethod.POST, "/login").permitAll()
		//나머지는 모든 것은 인증이 필요하하도록 지장함
		.anyRequest().authenticated().and()
		//예외 처리리를 실행하겠다.
		.exceptionHandling()
		//인증 지점을 알려주는 것입니다.
		.authenticationEntryPoint(exceptionHandler).and()
		.addFilterBefore(authenticationFilter,
				UsernamePasswordAuthenticationFilter.class);		
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(false);
		config.applyPermitDefaultValues();

		source.registerCorsConfiguration("/**", config);
		return source;
	}	
	
}

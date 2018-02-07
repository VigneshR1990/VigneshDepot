package com.vehicle.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vehicle.review.security.rest.LoginFilter;
import com.vehicle.review.security.rest.RestAuthenticationAccessDeniedHandler;
import com.vehicle.review.security.rest.RestAuthenticationEntryPoint;
import com.vehicle.review.security.rest.RestAuthenticationFailureHandler;
import com.vehicle.review.security.rest.RestAuthenticationSuccessHandler;
import com.vehicle.review.service.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	public WebSecurityConfig() {
		// TODO Auto-generated constructor stub
		super(true);
	}

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Autowired
	private RestAuthenticationFailureHandler restAuthenticationFailureHandler;

	@Autowired
	private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
				.accessDeniedHandler(restAuthenticationAccessDeniedHandler).and().formLogin()
				.successHandler(restAuthenticationSuccessHandler).failureHandler(restAuthenticationFailureHandler).and()
				.anonymous().and().servletApi().and().authorizeRequests().antMatchers("/**/*.jpg").permitAll()
				.antMatchers("/images/**").permitAll().antMatchers("/api/user/login").permitAll()
				.antMatchers("/api/user/add").permitAll().antMatchers("/api/automobile/add").permitAll().anyRequest()
				.authenticated().and()
				.addFilterBefore(new LoginFilter("/api/user/login", userDetailsService, authenticationManager()),
						UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}

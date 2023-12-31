package com.sloth.net.securityconfig;

import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sloth.net.repo.UserRepository;


@EnableWebSecurity
@Configuration
public class SecurityConfig {
	@Autowired
	UserRepository userRepo;
	@Autowired
	JwtFilter jwtFilter;
	//might use this later
//	@Bean
//	public AuthenticationManager authenticationManagerBean(
//			HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailService)
//					throws Exception{
//		return http.getSharedObject(AuthenticationManagerBuilder.class)
//			      .userDetailsService(userDetails)
//			      .passwordEncoder(bCryptPasswordEncoder)
//			      .and()
//			      .build();
//	}
	

	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new UserDetailsService() {
//
//			@Override
//			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//		};
//	}
	
	@Bean
	public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http
	  .csrf().disable()
      .authorizeHttpRequests()
      .antMatchers("/auth/**","/","/mvc/**","/xxx/**","/app/**","/user/**").permitAll()
      .anyRequest().authenticated();
//	  http
//	  .formLogin()
//	  .loginPage("/auth/login");
	  ;
	  
	  // the exception handling code ensures that the server will return 
	  //HTTP status 401 (Unauthorized) if any error occurs during authentication process
      http.exceptionHandling()
      .authenticationEntryPoint(
          (request, response, ex) -> {
              response.sendError(
                  HttpServletResponse.SC_UNAUTHORIZED,
                  ex.getMessage()
              );
          }
      );
        http.logout().deleteCookies("remove").invalidateHttpSession(false)
		.logoutUrl("/logout").logoutSuccessUrl("/");
      
      // add custom jwt filter to filter chain
	  http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	   return http.build();
	}

	
//	@Autowired
//	public void ConfigureGlobal(AuthenticationManagerBuilder authManger) throws Exception {
//		authManger.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
//	}
	
	@Bean 
	public WebSecurityCustomizer customizer() {
		return (web)->web.ignoring().antMatchers("/static/**","/music/**");
	}
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

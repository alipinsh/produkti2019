package com.example.demo;

import org.springframework.context.annotation.Configuration;  
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;  
import org.springframework.security.config.annotation.web.builders.HttpSecurity;  
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration  
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {  
    @Override  
    public void configure(HttpSecurity http) throws Exception {  
    	http  
        .authorizeRequests()  
	        .antMatchers( "/public/**", "/register", "/index").permitAll()
	        .anyRequest().authenticated()  
            .and()  
        .formLogin()  
            .loginPage("/login")  
            .failureUrl("/login-error")  
            .permitAll()
            .and()
        .logout()
        	.permitAll();
    }  
    @Override  
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
    	
    	
        auth.inMemoryAuthentication()  
            .withUser("user")  
            .password("{noop}pass")
            .roles("USER");  
    }  
}

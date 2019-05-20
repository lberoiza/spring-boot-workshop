package de.osp.springbootworkshop.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration @EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("user").password("{noop}pass").roles("REGISTER");
    }
	
	
	
	@Override protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/petshop/pets").hasAnyRole("ADMIN, REGISTER")
		.antMatchers(HttpMethod.POST, "/petshop/pets**").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/petshop/pets**").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.httpBasic()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}

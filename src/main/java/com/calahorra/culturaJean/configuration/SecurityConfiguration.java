package com.calahorra.culturaJean.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.calahorra.culturaJean.services.implementation.MemberService;

///Clase SecurityConfiguration:
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration 
{
	//Atributo:
	private final MemberService memberService;

	//Constructor:
	public SecurityConfiguration(MemberService memberService) 
	{
		this.memberService = memberService;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.cors(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers("/css/*", "/assets/img/**", "/js/*", "/vendor/bootstrap/css/*",
							"/vendor/jquery/*", "/vendor/bootstrap/js/*", "/api/v1/**", "/", "/registerAdd", "/aboutUs/visitor", "/help/visitor",
							"/register", "/product/products/visitor", "/product/products/filter", "/product/products/unique-sizes", "/stores/visitor", 
							"/product/moreDetails/visitor/**", "/error/**").permitAll();
					auth.anyRequest().authenticated();
				})
				.formLogin(login -> {
					login.loginPage("/login");
					login.loginProcessingUrl("/loginprocess");
					login.usernameParameter("username");
					login.passwordParameter("password");
					login.defaultSuccessUrl("/loginsuccess");
					login.permitAll();
				})
				.logout(logout -> {
					logout.logoutUrl("/logout");
					logout.logoutSuccessUrl("/login");
					logout.permitAll();
				})
				.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
	{
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(memberService);
		return provider;
	}

	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}


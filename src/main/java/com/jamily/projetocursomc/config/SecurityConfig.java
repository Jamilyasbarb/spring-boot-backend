package com.jamily.projetocursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jamily.projetocursomc.security.JWTAuthenticationFilter;
import com.jamily.projetocursomc.security.JWTAuthorizationFilter;
import com.jamily.projetocursomc.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	private static final String[]PUBLIC_MATCHERS = {
			"/h2-console/**",
	};
	
	private static final String[]PUBLIC_MATCHERS_GET= {
			"/produtos/**",
			"/categorias/**",
	};
	private static final String[]PUBLIC_MATCHERS_POST= {
			"/clientes",
			"/auth/forgot/**",
			"/estados/**",
			"/cidades/**",
	};
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			httpSecurity.headers().frameOptions().disable();
		}
		httpSecurity.cors().and().csrf().disable(); //chama CorsConfigurationSource e desativa o csrf
		httpSecurity.authorizeRequests()
		.antMatchers(PUBLIC_MATCHERS).permitAll() //endpoints sem autentificacao
		.antMatchers(HttpMethod.GET,PUBLIC_MATCHERS_GET).permitAll() //endpoints sem autentificacao, apenas get
		.antMatchers(HttpMethod.POST,PUBLIC_MATCHERS_POST).permitAll() //endpoints sem autentificacao, apenas post
		.antMatchers(PUBLIC_MATCHERS).permitAll() //endpoints sem autentificacao
		.anyRequest().authenticated(); //endpoints com autentificacao
		httpSecurity.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		httpSecurity.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST","GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
		
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

package com.jsfspringintegration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends AbstractSecurityWebApplicationInitializer {

	@Configuration
	@Order(1)
	public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
		
		@Autowired
		private CustomAutenticationProvider customAutenticationProvider;

		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(customAutenticationProvider);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
			.antMatchers("/javax.faces.resource/**", "/WEB-INF/plantillas/**", "/index.xhtml").permitAll()
			//.regexMatchers(".*/callFromSearch\\?search=\\&agregarNuevo/?.*", "/callFromSearchPart\\?search=\\&agregarNuevo/?.*", "/editPartner?.*", ".*/editFam?.*").hasRole("ADMIN")
			.anyRequest().authenticated().and().formLogin().loginPage("/").permitAll().and()
			.logout().permitAll().and().exceptionHandling().accessDeniedPage("/forbidden.xhtml");
		}

		@Bean(name = {"authenticationManager"})
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	}
}

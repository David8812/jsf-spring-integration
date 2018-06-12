package com.jsfspringintegration.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.jsfspringintegration.model.User;
import com.jsfspringintegration.repository.UserRepository;

@Component
public class CustomAutenticationProvider implements AuthenticationProvider {

	@Autowired
	UserRepository userRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String name = authentication.getName();

		Object credentials = authentication.getCredentials();

		if (credentials == null)
			credentials = "";

		if (!(credentials instanceof String)) {
			return null;
		}

		String password = credentials.toString();
		
		User user = userRepository.findByUserName(name);

		System.out.println("credential=> user: " + name + " password: " + password);
		
		// Optional<User> userOptional = users.stream().filter(u -> u.match(name,
		// password)).findFirst();

		if (user == null || !user.getPassword().equals(password)) {
			throw new BadCredentialsException("Authentication failed for " + name);
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		//System.out.println("Rol para: " + name + " es: " + user.getRoll());
		grantedAuthorities.add(new SimpleGrantedAuthority(user.getRoll()));
		return new UsernamePasswordAuthenticationToken(name, password, grantedAuthorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}

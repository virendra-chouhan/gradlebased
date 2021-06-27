package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.dao.UserRepository;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	Timestamp systemtime = new Timestamp(System.currentTimeMillis());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();

	@Override
	public UserDetails loadUserByUsername(String number) throws UsernameNotFoundException {
		User user = this.userRepository.findByMobile_no(number);
		if (user == null) {
			throw new UsernameNotFoundException("User not found this number: " + number);
		}
		return new org.springframework.security.core.userdetails.User(user.getMobile_no(), user.getUser_name(),
				new ArrayList<>());
	}

}
package com.blink.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blink.domain.admin.Admin;
import com.blink.domain.admin.AdminRepository;
import com.blink.enumeration.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	private final AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminRepository.findByName(username);
		List<GrantedAuthority> authorities = new ArrayList<>();
		if (admin.getRoleId().equals(Role.MASTER)) {
			authorities.add(new SimpleGrantedAuthority("ROLE_MASTER"));
		} else {
			authorities.add(new SimpleGrantedAuthority("ROLE_HOSPITAL"));
		}
		 
		return new User(admin.getName(), admin.getPassword(), authorities);
	}

}

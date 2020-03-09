package com.blink.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blink.entity.User;
import com.blink.entity.Role;
import com.blink.repository.RoleRepository;
import com.blink.repository.UserRepository;
import com.blink.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository adminRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public Iterable<User> selectAllUser() {

		Iterable<User> userList = adminRepo.findAll();
		return userList;
	}

	@Override
	@Transactional
	public void signupUser(User admin) {
		Role role = new Role();
		role.setId(2L);
		admin.setRole(role);
		admin.setEmail(admin.getHospital().getEmployeeEmail());
		
		admin.getHospital().setName(admin.getName());
		admin.getHospital().setRoleId(2L);
		adminRepo.save(admin);
	}

	@Override
	public Iterable<User> selectSomeUsers() {
		return adminRepo.findUsersByName("liangjinyong");
	}

}

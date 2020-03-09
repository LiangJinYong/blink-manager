package com.blink.service;

import com.blink.entity.User;

public interface UserService {

	Iterable<User> selectAllUser();

	void signupUser(User admin);

	Iterable<User> selectSomeUsers();

}

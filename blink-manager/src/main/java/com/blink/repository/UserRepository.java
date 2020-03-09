package com.blink.repository;

import org.springframework.data.repository.CrudRepository;

import com.blink.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Iterable<User> findUsersByName(String name);
}

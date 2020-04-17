package com.blink.domain.admin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends CrudRepository<Admin, Long> {

	Admin findByName(String username);
	
	@Query("SELECT COUNT(a.id) FROM Admin a WHERE a.name=:username")
	int findCountByName(@Param("username") String username);
}

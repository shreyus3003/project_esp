package com.foodys.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodys.app.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	
	public User findUserByEmail(String email);

	public User findUserById(long id);
	
}

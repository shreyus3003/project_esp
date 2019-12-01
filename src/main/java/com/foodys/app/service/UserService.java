package com.foodys.app.service;

import java.util.List;

import com.foodys.app.models.User;

public interface UserService {
	
	public User findUserByEmail(String email);
	public List<User> findAll();
	public User findUserById(long id);
	public long save(User user);
	public User update(User user);
	public void delete(long id);

}

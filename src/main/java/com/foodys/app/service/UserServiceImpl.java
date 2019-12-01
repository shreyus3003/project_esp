package com.foodys.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodys.app.models.User;
import com.foodys.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository urepo;

	@Override
	public User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return urepo.findUserByEmail(email);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return urepo.findAll();
	}

	@Override
	public User findUserById(long id) {
		// TODO Auto-generated method stub
		return urepo.findUserById(id);
	}

	@Override
	public long save(User user) {
		// TODO Auto-generated method stub
		User inserted_user = urepo.save(user);
		return inserted_user.getId();
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

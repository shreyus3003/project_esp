package com.foodys.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.foodys.app.models.Role;
import com.foodys.app.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository urepo;
	
	@Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//    	String password = ;
    	
    	
    	com.foodys.app.models.User user =  urepo.findUserByEmail(s);
    	List<String> roles = new ArrayList<String>();
    	for(Role role: user.getRoles()) {
    		roles.add(role.getRole());
    	}
    	System.out.println("******"+roles.get(0)+"********");
    	
    	UserDetails userDetails = User.withUsername(s)
                .password(user.getPassword())
                .roles(roles.get(0)).build();
    	
    	return userDetails; 
    	
    }
    
    
    
    
}
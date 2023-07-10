package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.smart.entities.User;
import com.smart.repo.UserRepo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{

	
	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.getUserByUserName(username);
		if(user==null)
		{
			throw new UsernameNotFoundException("User not found");
		} 
		CustomUserDetail customUserDetail=new CustomUserDetail(user);
		return customUserDetail;
	}

}

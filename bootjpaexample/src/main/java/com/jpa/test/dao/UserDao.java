package com.jpa.test.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jpa.test.entities.User;

public interface UserDao extends CrudRepository<User, Integer>{
	public List<User> findByName(String name);
	public List<User> findByCity(String city); 
	public void deleteByName(String name);
	
	@Query(value="select * from user where name=? and city=?",nativeQuery = true)
	public List<User> getByNameAndCity(String name,String city);
	@Query(value="select * from user where name=? and status=?",nativeQuery = true)
	public List<User> getByNameAndStatus(String name,String status);

}

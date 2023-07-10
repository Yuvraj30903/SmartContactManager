package com.jpa.test;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.jpa.test.dao.UserDao;
import com.jpa.test.entities.User;

@SpringBootApplication
public class BootjpaexampleApplication {

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(BootjpaexampleApplication.class, args);
		UserDao userdao = context.getBean(UserDao.class); 
//		List <User> users=List.of(u1,u2,u3);
//		userdao.saveAll(users);  
//		for (User u : userdao.findAll()) {
//			System.out.println(u);
//		}   
//		List<User> findByCity = userdao.findByCity(null); 
		userdao.getByNameAndStatus("name1","status1").forEach(n->System.out.println(n));
	}

}

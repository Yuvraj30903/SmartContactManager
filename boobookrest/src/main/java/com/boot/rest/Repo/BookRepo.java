package com.boot.rest.Repo;

 
import org.springframework.data.repository.CrudRepository; 

import com.boot.rest.entities.Book;   
public interface BookRepo extends CrudRepository<Book, Integer>{
	public Book findById(int id);

}

package com.boot.rest.services;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component; 
import com.boot.rest.Repo.BookRepo;
import com.boot.rest.entities.Book;
@Component
public class BookService {
	 
	@Autowired
	private BookRepo bookRepo;
	
	public List<Book> getAllBooks()
	{
		return (List<Book>)bookRepo.findAll();
	}
	public Book getBookById(int id)
	{ 
		
		return bookRepo.findById(id);
		
	}
	public void addBook(Book b)
	{ 
		bookRepo.save(b);
	}
	public void updateBookById(Book book, int id) {
		book.setBookId(id);	
		bookRepo.save(book);
	}
	public void deleteBookById(int id) {
		bookRepo.deleteById(id);
	}
	public void deleteAllBooks() {
		bookRepo.deleteAll();
		
	}
}

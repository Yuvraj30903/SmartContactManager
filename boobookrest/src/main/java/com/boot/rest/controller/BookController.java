package com.boot.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RestController;

import com.boot.rest.entities.Book;
import com.boot.rest.services.BookService;

@RestController
public class BookController {

	@Autowired
	private BookService bookService; 
	
	@GetMapping("/books") 
	public ResponseEntity<List<Book>> getAllBooks()
	{
		List<Book> list=bookService.getAllBooks();
		if(list.size()<=0)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}   
		
		
		return ResponseEntity.of(Optional.of(list));
	}
	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable int id)
	{
		Book b=bookService.getBookById(id);
		if(b==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(b));
	}
	
	
	@PostMapping("/books") 
	public ResponseEntity<Class<Void>> postBook(@RequestBody Book book)
	{ 
		try {
			bookService.addBook(book); 
			return ResponseEntity.of(Optional.of(void.class));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	} 
	
	
	@PutMapping("/books/{id}")
	public ResponseEntity<Class<Void>> putBook(@RequestBody Book book,@PathVariable int id)
	{
		
		
		try {

			bookService.updateBookById(book,id);
			return ResponseEntity.of(Optional.of(void.class));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@DeleteMapping("/books/{id}")
	public ResponseEntity<Class<Void>> deleteBook(@PathVariable int id)
	{

		try {
			bookService.deleteBookById(id);
			return ResponseEntity.of(Optional.of(void.class));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	@DeleteMapping("/books")
	public ResponseEntity<Class<Void>> deleteAllBooks()
	{
		try {
			bookService.deleteAllBooks();
			return ResponseEntity.of(Optional.of(void.class));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}  
}

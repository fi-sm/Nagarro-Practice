/**
 * 
 */
package com.nagarro.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.bookstore.bean.MediaCoverage;
import com.nagarro.bookstore.bean.Message;
import com.nagarro.bookstore.entity.Book;
import com.nagarro.bookstore.entity.Inventory;
import com.nagarro.bookstore.service.BookService;

/**
 * @author samarjeetyadav
 *
 */


@RestController
public class BookController {
	
	
	
	
	@Autowired
	BookService bookService;
	
	/**
	 * @Desc : Fetch Booked from master list
	 * @return
	 */
	@RequestMapping("/books")
	public List<Book> getBooks()
	{
		
		return bookService.getBooks();
	}
	

	
	/**
	 * @Desc Add book in master list and in inventory
	 * @param book
	 * @return
	 */
	@RequestMapping( method=RequestMethod.POST, value="/books")
	public Message addBook(@RequestBody Book book)
	{
		
		 return bookService.addBook(book);
	}
	
	
	
	/**
	 * @Desc Decrease book copy count in inventory
	 * @param book
	 * @return
	 */
	@RequestMapping( method=RequestMethod.PUT, value="/buyBook")
	public Message buyBook(@RequestBody Book book)
	{
		
		return  bookService.buyBook(book);
	}
	
	
	/**
	 * @Desc: Search book with search string in context of Title, author and ISBN
	 * @param searchString
	 * @return
	 */
	@RequestMapping("/search")
	@ResponseBody
	public List<Inventory> searchBooks(@RequestParam String searchString) {
		System.out.println("searchString:"+searchString);
	   return bookService.searchBooks(searchString);
	}
	
	
	/**
	 * @Desc: Search book with search string in matching ISBN
	 * @param isbn
	 * @return
	 */
	@RequestMapping("/isbn")
	@ResponseBody
	public List<Inventory> searchByIsbnBooks(@RequestParam String isbn) {
		
	   return bookService.searchBooksByIsbn(isbn);
	}
	
	
	@RequestMapping("/searchAll")
	@ResponseBody
	public List<Inventory> searchAllBooks(@RequestParam String searchString) {
		System.out.println("searchString:"+searchString);
	   return bookService.searchBooks();
	}
	
	
	
	/**
	 * @DESC Media Coverage of book
	 * @param searchString
	 * @return
	 */
	@RequestMapping("/mediacoverage")
	@ResponseBody
	public MediaCoverage mediaCoverageOfBook(@RequestParam String searchString) {
		
	   return bookService.mediaCoverageOfBook(searchString);
	}
	
	
	
	
	
	@RequestMapping("/books/{id}")
	public Book getBook(@PathVariable String id)
	{
		
		
		return bookService.getBook(id);
		
	}
	
	
	
	

}

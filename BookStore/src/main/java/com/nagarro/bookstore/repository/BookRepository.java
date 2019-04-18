/**
 * 
 */
package com.nagarro.bookstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.bookstore.entity.Book;

/**
 * @author samarjeetyadav
 *
 */

@Repository
public interface BookRepository  extends CrudRepository<Book, String>{

	public Book findBookById(String id);
	public Book findBookByIsbn(String isbn);
}

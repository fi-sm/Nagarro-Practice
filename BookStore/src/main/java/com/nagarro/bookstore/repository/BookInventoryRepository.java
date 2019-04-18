package com.nagarro.bookstore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nagarro.bookstore.entity.Inventory;

public interface BookInventoryRepository extends CrudRepository<Inventory, String> {

	
	public Inventory findBookInventoryById(String id);
	
	public Inventory findByBookId(String booId);
	public List<Inventory> findByBookTitleIgnoreCaseContaining(String bookTitle);
	public List<Inventory> findByBookAuthorIgnoreCaseContaining(String bookAuthor);
	public List<Inventory> findByBookIsbn(String bookIsbn);
	
	
}

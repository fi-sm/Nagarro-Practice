/**
 * 
 */
package com.nagarro.bookstore.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagarro.bookstore.bean.MediaCoverage;
import com.nagarro.bookstore.bean.Message;
import com.nagarro.bookstore.bean.PostDetail;
import com.nagarro.bookstore.entity.Book;
import com.nagarro.bookstore.entity.Inventory;
import com.nagarro.bookstore.repository.BookInventoryRepository;
import com.nagarro.bookstore.repository.BookRepository;

/**
 * @author samarjeetyadav
 *
 */

@Service
public class BookService {
	
	
	@Autowired
	BookRepository bookRepository;
	@Autowired
	BookInventoryRepository bookInventoryRepository;
	
	public List<Book> getBooks(){
		
		List<Book> bookList=new ArrayList<>();
		
		 bookRepository.findAll().forEach(bookList :: add);
		 return bookList;
	}
	
	


public List<Inventory> searchBooks(String searchString){
	
	List<Inventory> list1=bookInventoryRepository.findByBookTitleIgnoreCaseContaining(searchString);
	List<Inventory> list2=bookInventoryRepository.findByBookAuthorIgnoreCaseContaining(searchString);
	List<Inventory> list3=bookInventoryRepository.findByBookIsbn(searchString);
	
	
	List<Inventory> newList = Stream.of(list1, list2, list3)
            .flatMap(Collection::stream)
            .collect(Collectors.toList()); 
	  List<Inventory> listWithoutDuplicates = newList.stream()
			     .distinct()
			     .collect(Collectors.toList());
		
		return listWithoutDuplicates;
		
	}

public List<Inventory> searchBooksByIsbn(String searchString){
	
	
	List<Inventory> list3=bookInventoryRepository.findByBookIsbn(searchString);
	
		
		return list3;
		
	}

public List<Inventory> searchBooks(){
	
	
	List<Inventory>  list=new ArrayList<>();
	
	
	 bookInventoryRepository.findAll().forEach(list ::  add);
	
	 return list;
}

	
	
public  synchronized Message addBook(Book book)
{
	
	Message message=new Message();
	try
	{
		
	
	
		Book bookInDB=bookRepository.findBookByIsbn(book.getIsbn());
		if(null!=bookInDB)
		{
		Inventory bookInventory=	bookInventoryRepository.findByBookId(bookInDB.getId());
		if(null!=bookInventory)
		{
			bookInventory.setBookCopyCount(bookInventory.getBookCopyCount()+1);
			
		}
		else
		{
			bookInventory=new Inventory();
			bookInventory.setBookCopyCount(1);
			bookInventory.setBook(bookInDB);
		}
		
		bookInventoryRepository.save(bookInventory);
		}
		else
		{
			bookRepository.save(book);
			Inventory bookInventory=new Inventory();
			bookInventory.setBookCopyCount(1);
			bookInventory.setBook(book);
			bookInventoryRepository.save(bookInventory);
		}
		
		message.setMessageId("200");
		message.setMessage("Book is added.");
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		
		message.setMessageId("405");
		message.setMessage("Error occured in adding book, please try after some time.");
	}
	
	return message;
}
	
	
	





public  synchronized Message buyBook(Book book)
{
	
	
	Message message=new Message();
	try
	{
		Book bookInDB=bookRepository.findBookByIsbn(book.getIsbn());
		if(null!=bookInDB)
		{
		Inventory bookInventory=	bookInventoryRepository.findByBookId(bookInDB.getId());
		if(null!=bookInventory && bookInventory.getBookCopyCount() >0)
		{
			bookInventory.setBookCopyCount(bookInventory.getBookCopyCount()-1);
			bookInventoryRepository.save(bookInventory);
			
			message.setMessageId("200");
			message.setMessage("Your order is successfully placed");
			
		}
		else
		{
			
			message.setMessageId("404");
			message.setMessage("Book is not available");
		}
		
		
		}
		else
		{
			
			message.setMessageId("404");
			message.setMessage("Book is not available");
		}
		
	}
	catch (Exception e)
	{
		e.printStackTrace();
		
		message.setMessageId("405");
		message.setMessage("Error occured in buying book, please try after some time.");
	}
	
	return message;
}


public MediaCoverage mediaCoverageOfBook(String searchString)
{
	
	MediaCoverage mediaCoverage=new MediaCoverage();
	
	try
	{
		RestTemplate restTemplate = new RestTemplate();
		PostDetail posts[] = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts",PostDetail[].class);
		
		
		List<PostDetail> postsList=Arrays.asList(posts);
		
		List<String> titles =postsList.stream().filter(
		
				x -> {
					
					if((null!=searchString && !"".equals(searchString) && (x.getTitle().toUpperCase()).contains(searchString.toUpperCase())) ||
					(null!=searchString && !"".equals(searchString) && (x.getBody().toUpperCase()).contains(searchString.toUpperCase())))
					return true;
			else
				return false;
			
		}
		
		
		).map(PostDetail::getTitle)
		.collect(Collectors.toList());
		mediaCoverage.setErrorCode("OK");
		if(null==titles || titles.size()==0)
		{
			mediaCoverage.setErrorCode("NOT_FOUND");
			mediaCoverage.setErrorDeescription("No Match found");	
		}
			
		mediaCoverage.setTitles(titles);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		mediaCoverage.setErrorCode("RemoteApiError");
		mediaCoverage.setErrorDeescription("Error occured in system integeration, please try after some time.");
		
	}

return mediaCoverage;
}


public  Book getBook(String id){

return bookRepository.findBookById(id);
}

	


}

package com.nagarro.bookstore;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.nagarro.bookstore.bean.MediaCoverage;
import com.nagarro.bookstore.bean.Message;
import com.nagarro.bookstore.controller.BookController;
import com.nagarro.bookstore.entity.Book;
import com.nagarro.bookstore.entity.Inventory;
import com.nagarro.bookstore.service.BookService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BookController.class)
public class BookStoreTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;
	
	@Test
	public void createBook() throws Exception {
		
		Message msg=new Message();
		msg.setMessageId("200");
		msg.setMessage("Book is added.");
		
		
		Mockito.when(
				bookService.addBook(
						Mockito.any(Book.class))).thenReturn(msg);

		

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/books")
				.accept(MediaType.APPLICATION_JSON).content("{\"isbn\":\"434\",\"title\":\"Cooking Book\",\"author\":\"AR Rahman\",\"price\":3.0}")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
System.out.println(response.getContentAsString());
		assertEquals(200, response.getStatus());

		

	} 
	
	
	
	
	@Test
	public void createBookNegative() throws Exception {
		
		Message msg=new Message();
		msg.setMessageId("200");
		msg.setMessage("Book is added.");

		
		
		Mockito.when(
				bookService.addBook(
						Mockito.any(Book.class))).thenReturn(msg);

		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/books")
				.accept(MediaType.APPLICATION_JSON).content("")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(200, response.getStatus());
		assertEquals("{\"messageId\":\"200\",\"message\":\"Book is added.\"}", response.getContentAsString());

		

	} 
	
	
	
	@Test
	public void searchBook() throws Exception {

		List<Inventory> list=new ArrayList<>();
		Mockito.when(
				bookService.searchBooks(
						Mockito.anyString())).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/search?searchString=434456").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		
		String expected = "[]";
System.out.println("*****************:"+result.getResponse()				.getContentAsString());
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	
	
	@Test
	public void searchMediacCoverage() throws Exception {

		MediaCoverage mediaCoverage=new MediaCoverage();
		Mockito.when(
				bookService.mediaCoverageOfBook(
						Mockito.anyString())).thenReturn(mediaCoverage);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/search?searchString=s").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		
		String expected = "[]";
System.out.println("*****************:"+result.getResponse().getContentAsString());
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}

}

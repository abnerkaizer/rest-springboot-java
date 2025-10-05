package com.abnerkaizer.rest_springboot_java.unitetests.mapper.mocks;

import com.abnerkaizer.rest_springboot_java.data.dto.v1.BookDTO;
import com.abnerkaizer.rest_springboot_java.model.Book;

import java.util.ArrayList;
import java.util.List;

public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookDTO mockDTO() {
        return mockDTO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setAuthor("Author Test" + number);
        book.setLaunchDate("Launch Date Test" + number);
        book.setTitle("Title Test" + number);
        book.setId(number.longValue());
        book.setPrice(number.doubleValue());
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setAuthor("Author Test" + number);
        book.setLaunchDate("Launch Date Test" + number);
        book.setTitle("Title Test" + number);
        book.setId(number.longValue());
        book.setPrice(number.doubleValue());
        return book;
    }

}
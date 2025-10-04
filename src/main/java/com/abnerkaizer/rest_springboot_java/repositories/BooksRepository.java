package com.abnerkaizer.rest_springboot_java.repositories;

import com.abnerkaizer.rest_springboot_java.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Long> {
}

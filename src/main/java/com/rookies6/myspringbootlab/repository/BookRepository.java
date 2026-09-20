package com.rookies6.myspringbootlab.repository;

import com.rookies6.myspringbootlab.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository <Book, Long> {

    Optional<Book> findByIsbn(String isbn); // NVL(isbn, '없는번호') 처리 널이면 없는 번호처럼 널값을 처리함

    List<Book> findByAuthor(String author);
}

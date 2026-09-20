package com.rookies6.myspringbootlab;


import com.rookies6.myspringbootlab.entity.Book;
import com.rookies6.myspringbootlab.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class BookRepositoryTest {

    @Autowired //BookRepository에서 가져온 구현체? 들을 가져와서 빈으로 처리함
    private BookRepository bookRepository;

    @Test
    @Rollback(false)
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    void testCreateBook() {
        Book book = new Book();

        book.setTitle("스프링 부트 입문");
        book.setAuthor("홍길동");
        book.setIsbn("9788956746425");
        book.setPrice(30000);
        book.setPublishDate(LocalDate.of(2025, 5, 7));

        Book savedBook = bookRepository.save(book);  //저장 그니까 북 객체에 있는 데이터들 넣고 save하는 순간 디비에 인설트

        assertThat(savedBook.getId()).isNotNull(); // aeertThat == 검증 메소드

        assertThat(savedBook.getTitle()).isEqualTo("스프링 부트 입문");
    }

    void testFindByAuthor() {
        Book book = new Book();

        book.setTitle("JPA 프로그래밍");
        book.setAuthor("박둘리");
        book.setIsbn("9788956746432");

        bookRepository.save(book);

        List<Book> books = bookRepository.findByAuthor("박둘리"); // select

        assertThat(books).isNotEmpty(); //아예 없는지

        assertThat(books.get(0).getTitle()).isEqualTo("JPA 프로그래밍");
    }

    @Test
    void testUpdateBook() {

        Book book = new Book();

        book.setTitle("스프링 부트 입문");
        book.setAuthor("홍길동");
        book.setPrice(30000);

        Book savedBook = bookRepository.save(book);

        savedBook.setPrice(35000);

        Book updatedBook = bookRepository.save(savedBook);

        assertThat(updatedBook.getPrice()).isEqualTo(35000);
    }

    @Test
    void testDeleteBook() {
        Book book = new Book();

        book.setTitle("스프링 부트 입문");

        Book savedBook = bookRepository.save(book);

        Long bookId = savedBook.getId();

        bookRepository.deleteById(bookId);

        Optional<Book> deletedBook = bookRepository.findById(bookId);
        assertThat(deletedBook).isEmpty();
    }
}


package com.rookies6.myspringbootlab.controller;
import com.rookies6.myspringbootlab.entity.Book;
import com.rookies6.myspringbootlab.exception.BusinessException;
import com.rookies6.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController 
@RequestMapping("api/books")
@RequiredArgsConstructor
public class BookRestController {

    private final BookRepository bookRepository;

    //모두 조회
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }
    //단일조회
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) { //경로에 있는 id를 가져옴
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book not found with id: " //에러 코드 처리
                + id, HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(book);
    }
    // 3. ISBN으로 도서 조회 (GET /api/books/isbn/{isbn})
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("Book not found with isbn: " 
                + isbn, HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(book);
    }
    // 4. 새 도서 등록 (POST /api/books)
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        // 이미 존재하는 ISBN인지 중복 검사
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new BusinessException("Book already exists with ISBN: " + book.getIsbn(), HttpStatus.CONFLICT);
        }
        Book savedBook = bookRepository.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }
    // 5. 도서 정보 수정 (PUT /api/books/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        // 1) 기존 책을 먼저 찾는다 (없으면 404)
        Book existBook = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book not found with id: " + id, HttpStatus.NOT_FOUND));
        // 2) 내용 업데이트
        existBook.setTitle(bookDetails.getTitle());
        existBook.setAuthor(bookDetails.getAuthor());
        existBook.setIsbn(bookDetails.getIsbn());
        existBook.setPrice(bookDetails.getPrice());
        existBook.setPublishDate(bookDetails.getPublishDate());
        // 3) 저장하고 리턴
        Book updatedBook = bookRepository.save(existBook);
        return ResponseEntity.ok(updatedBook);
    }
    // 6. 도서 삭제 (DELETE /api/books/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        // 책이 존재하는지 먼저 확인 (없으면 404)
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book not found with id: " + id, HttpStatus.NOT_FOUND));
        bookRepository.delete(book);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}

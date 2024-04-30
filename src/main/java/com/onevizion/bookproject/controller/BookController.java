package com.onevizion.bookproject.controller;

import com.onevizion.bookproject.api.BookData;
import com.onevizion.bookproject.entity.Book;
import com.onevizion.bookproject.repository.BookRepository;
import com.onevizion.bookproject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "/list-sorted-by-title", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<BookData>> findAllSortedByTitle() {
        // Note! return and accept BookData object not to expose table data to users
        List<Book> allSortedByTitle = bookService.findAllSortedByTitle();
        return ResponseEntity.ok(allSortedByTitle.stream()
                .map(book ->
                        new BookData(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription()))
                .toList());
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookData> addBook(@RequestBody BookData bookData) {
        // TODO Validation on bookData if needed
        // Note! return and accept BookData object not to expose table data to users
        Book book = new Book();
        book.setId(bookData.getId());
        book.setTitle(bookData.getTitle());
        book.setAuthor(bookData.getAuthor());
        book.setDescription(bookData.getDescription());
        bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookData);
    }

    @GetMapping(path = "/list-grouped", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Map<String, List<BookData>>> findAllGroupedByAuthor() {
        // Note! return BookData objects not to expose table data to users
        List<Book> allBooks = bookService.findAll();
        return ResponseEntity.ok(allBooks
                .stream()
                        .map(book -> new BookData(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription()))
                .collect(Collectors.groupingBy(BookData::getAuthor)));
    }


    @PostMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Map<String, Integer>> searchBySymbol(@RequestParam String symbol) {
        List<Book> books = bookService.findAll();

        Map<String, Integer> authorSymbolOccurrences = books.stream()
                .filter(book -> StringUtils.countOccurrencesOf(book.getTitle().toLowerCase(), symbol.toLowerCase()) > 0)
                .limit(10)
                .collect(Collectors.groupingBy(
                        Book::getAuthor,
                        Collectors.summingInt(book -> StringUtils.countOccurrencesOf(book.getTitle().toLowerCase(), symbol.toLowerCase()))));

        return ResponseEntity.ok(authorSymbolOccurrences);
    }
}

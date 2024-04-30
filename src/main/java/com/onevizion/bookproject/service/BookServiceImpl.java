package com.onevizion.bookproject.service;

import com.onevizion.bookproject.entity.Book;
import com.onevizion.bookproject.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAllSortedByTitle() {
        return bookRepository.findAll(Sort.by(Sort.Direction.DESC, "title"));
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}

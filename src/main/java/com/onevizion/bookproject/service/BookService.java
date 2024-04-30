package com.onevizion.bookproject.service;

import com.onevizion.bookproject.entity.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);

    List<Book> findAllSortedByTitle();

    List<Book> findAll();
}

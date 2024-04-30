package com.onevizion.bookproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id", nullable = false, length = 150, unique = true)
    private Integer id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "author", nullable = false, length = 150)
    private String author;

    @Column(name = "description", length = 150)
    private String description;
}

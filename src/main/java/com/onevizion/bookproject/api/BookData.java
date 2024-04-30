package com.onevizion.bookproject.api;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BookData {
    private Integer id;
    private String title;
    private String author;
    private String description;
}

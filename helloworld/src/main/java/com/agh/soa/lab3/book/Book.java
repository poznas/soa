package com.agh.soa.lab3.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Book {

    private String title;
    private String author;
    private BookType type;
    private double price;
    private Currency currency;
    private int pages;

    private boolean selected;

}

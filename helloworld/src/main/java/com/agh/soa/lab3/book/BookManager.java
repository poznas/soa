package com.agh.soa.lab3.book;

import lombok.Getter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

import static com.agh.soa.lab3.book.BookType.*;
import static com.agh.soa.lab3.book.Currency.*;
import static java.util.stream.Collectors.toList;

@Getter
@SessionScoped
@ManagedBean(name = "bookManager")
public class BookManager {

    public static final List<Book> books = List.of(
            Book.of("My Action", "John Parówa", ACTION, 23.00, PLN, 320, false),
            Book.of("My Drama", "Chuck Parówa", DRAMA, 20.50, PLN, 321, false),
            Book.of("My Romance", "Juan Parówa", ROMANCE, 23.50, EUR, 322, false),
            Book.of("His Romance", "Rick Parówa", ROMANCE, 28.50, EUR, 32, false),
            Book.of("Her Romance", "Rick Parówa", ROMANCE, 24.50, PLN, 324, false),
            Book.of("My Horror", "John Parówa", HORROR, 21.50, USD, 325, false)
    );

    private BookFilter includeFilter = new BookFilter(true);
    private BookFilter excludeFilter = new BookFilter(false);

    public List<Book> getBooks() {
        return books.stream()
                .filter(includeFilter)
                .filter(excludeFilter)
                .collect(toList());
    }

    public BookType[] getBookTypes() {
        return BookType.values();
    }

    public Currency[] getCurrencies() {
        return Currency.values();
    }

    public long getSelectedCount() {
        return books.stream().filter(Book::isSelected).count();
    }

    public double getTotalPrice() {
        return books.stream().filter(Book::isSelected)
                .mapToDouble(book -> book.getCurrency().plnValue(book.getPrice()))
                .sum();
    }

    public void refresh() {

    }
}

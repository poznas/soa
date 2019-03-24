package com.agh.soa.lab3.book.predicate;

import com.agh.soa.lab3.book.Book;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static java.util.Objects.nonNull;

@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class BookPredicate<P> implements Predicate<Book> {

    protected P param;
    protected BiPredicate<Book, P> predicate;

    @Override
    public boolean test(Book book) {
        return predicate.test(book, param);
    }

    public boolean shouldTest() {
        return nonNull(param);
    }
}

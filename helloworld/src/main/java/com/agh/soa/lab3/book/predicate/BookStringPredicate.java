package com.agh.soa.lab3.book.predicate;

import com.agh.soa.lab3.book.Book;

import java.util.function.BiPredicate;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public class BookStringPredicate extends BookPredicate<String> {

    @Override
    public boolean shouldTest() {
        return isNotBlank(param);
    }

    public static BookStringPredicate of (String param, BiPredicate<Book, String> predicate) {
        var stringPredicate = new BookStringPredicate();
        stringPredicate.setPredicate(predicate);
        stringPredicate.setParam(param);
        return stringPredicate;
    }
}

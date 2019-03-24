package com.agh.soa.lab3.book.predicate;

import com.agh.soa.lab3.book.Book;

import java.util.function.BiPredicate;

public class BookEnumPredicate extends BookPredicate<Enum<?>>{

    @Override
    public boolean shouldTest() {
        return super.shouldTest() && !"ANY".equals(param.name());
    }

    public static BookEnumPredicate of (Enum<?> param, BiPredicate<Book, Enum<?>> predicate) {
        var enumPredicate = new BookEnumPredicate();
        enumPredicate.setPredicate(predicate);
        enumPredicate.setParam(param);
        return enumPredicate;
    }
}

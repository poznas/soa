package com.agh.soa.lab6.exception;

import java.util.function.Supplier;

public class LibraryException extends Exception implements Supplier<LibraryException> {

    public static final LibraryException NO_SUCH_BOOK
            = new LibraryException("Book not found");
    public static final LibraryException NO_BOOK_COPIES_TO_BORROW
            = new LibraryException("No available copies to borrow");
    public static final LibraryException NO_SUCH_BOOK_TO_RETURN
            = new LibraryException("Attempt to return a book without borrowing it in the first place");

    private LibraryException(String message) {
        super(message);
    }

    @Override
    public LibraryException get() {
        return this;
    }
}

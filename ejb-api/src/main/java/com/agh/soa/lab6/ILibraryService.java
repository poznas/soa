package com.agh.soa.lab6;

import com.agh.soa.lab6.exception.LibraryException;
import com.agh.soa.lab6.model.LibraryBook;
import com.agh.soa.lab6.model.Reader;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public interface ILibraryService {

    void borrowBook(@NotNull Reader reader, @NotNull Long bookId,
                    @NotNull LocalDateTime returnDueDate) throws Exception;

    void returnBook(@NotNull Reader reader, @NotNull Long bookId) throws LibraryException;

    Reader getReader(Long id);

    void mergeReader(@NotNull Reader reader);

    Reader insertReader(@NotNull Reader reader);

    List<LibraryBook> getAvailableBooks();
}

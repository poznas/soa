package com.agh.soa.lab6;

import static com.agh.soa.lab6.exception.LibraryException.NO_BOOK_COPIES_TO_BORROW;
import static com.agh.soa.lab6.exception.LibraryException.NO_SUCH_BOOK;
import static com.agh.soa.lab6.exception.LibraryException.NO_SUCH_BOOK_TO_RETURN;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

import com.agh.soa.lab6.dao.AuthorRepository;
import com.agh.soa.lab6.dao.BorrowRepository;
import com.agh.soa.lab6.dao.LibraryBookRepository;
import com.agh.soa.lab6.dao.ReaderRepository;
import com.agh.soa.lab6.exception.LibraryException;
import com.agh.soa.lab6.model.Borrow;
import com.agh.soa.lab6.model.LibraryBook;
import com.agh.soa.lab6.model.Reader;
import com.agh.soa.lab7.LibraryMessageProducer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Stateful
@Remote(ILibraryService.class)
public class LibraryService implements ILibraryService {

    @EJB
    private AuthorRepository authorRepository;
    @EJB
    private LibraryBookRepository bookRepository;
    @EJB
    private ReaderRepository readerRepository;
    @EJB
    private BorrowRepository borrowRepository;
    @EJB
    private LibraryMessageProducer messageProducer;

    @Override
    public void borrowBook(@NotNull Reader reader, @NotNull Long bookId,
                           @NotNull LocalDateTime returnDueDate) throws LibraryException {

        var targetBook = getBook(bookId);

        int available = of(targetBook)
                .map(LibraryBook::getAvailableCopiesCount)
                .filter(count -> count > 0)
                .orElseThrow(NO_BOOK_COPIES_TO_BORROW);

        targetBook.setAvailableCopiesCount(available - 1);

        Borrow borrow = new Borrow();
        borrow.setBook(targetBook);
        borrow.setReader(reader);
        borrow.setBorrowDateTime(LocalDateTime.now());
        borrow.setReturnDateTime(returnDueDate);

        borrowRepository.merge(borrow);

        messageProducer.sendMessage(reader.getId(), "Book borrowed: " + targetBook.getTitle());
    }

    @Override
    public void returnBook(@NotNull Reader reader, @NotNull Long bookId) throws LibraryException {

        var targetBook = getBook(bookId);

        Predicate<Borrow> isTargetBookBorrow = borrow ->
                of(borrow).map(Borrow::getBook).map(LibraryBook::getId)
                        .filter(id -> targetBook.getId().equals(id)).isPresent();

        Borrow borrow = reader.getBorrows().stream().filter(isTargetBookBorrow).findFirst()
                .orElseThrow(NO_SUCH_BOOK_TO_RETURN);

        borrowRepository.delete(borrow);

        var updatedBook = getBook(bookId);

        int available = targetBook.getAvailableCopiesCount();
        updatedBook.setAvailableCopiesCount(available + 1);

        bookRepository.merge(updatedBook);

        messageProducer.sendMessage(reader.getId(), "Book returned: " + updatedBook.getTitle());

        if(available == 0) {
          messageProducer.sendMessage(updatedBook.getTitle() + " is now available!");
        }
    }

    @Override
    public Reader getReader(Long id) {
        return readerRepository.getEntity(id);
    }

    @Override
    public void mergeReader(Reader reader) {
        readerRepository.merge(reader);
    }

    @Override
    public Reader insertReader(Reader reader) {
        var newReader = new Reader();
        newReader.setFirstName(reader.getFirstName());
        newReader.setSecondName(reader.getSecondName());

        return readerRepository.insert(newReader);
    }

    @Override
    public List<LibraryBook> getAvailableBooks() {
        return bookRepository.getEntities().stream()
                .filter(book -> book.getAvailableCopiesCount() > 0)
                .collect(toList());
    }

    private LibraryBook getBook(Long bookId) throws LibraryException {
        return of(bookId).map(bookRepository::getEntity).orElseThrow(NO_SUCH_BOOK);
    }
}

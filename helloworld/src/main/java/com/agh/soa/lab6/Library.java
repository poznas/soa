package com.agh.soa.lab6;

import com.agh.soa.lab6.model.Reader;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.time.LocalDateTime;

import static java.util.Optional.of;

@Getter
@SessionScoped
@ManagedBean(name = "library")
public class Library {

    @EJB(lookup = "java:global/ejb/LibraryService")
    private ILibraryService libraryService;

    private Reader currentReader = new Reader();

    @Setter
    private Long bookId;

    public void loadReader() {
        currentReader = of(currentReader).map(Reader::getId)
                .map(libraryService::getReader).orElse(new Reader());
    }

    public void mergeReader() {
        libraryService.mergeReader(currentReader);
    }

    public void insertReader() {
        currentReader = libraryService.insertReader(currentReader);
    }

    public void borrowBook() {
        try {
            libraryService.borrowBook(currentReader, bookId, LocalDateTime.now());
            loadReader();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnBook() {
        try {
            libraryService.returnBook(currentReader, bookId);
            loadReader();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.agh.soa.lab6;

import static com.agh.soa.lab7.JmsPropertyName.READER_ID;
import static java.util.Optional.of;

import com.agh.soa.lab6.model.Reader;
import com.agh.soa.lab7.LibraryConsumer;
import com.agh.soa.lab7.LibraryContext;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.TextMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
@Named
@Getter
@SessionScoped
public class Library implements Serializable {

    @EJB(lookup = "java:global/ejb/LibraryService")
    private ILibraryService libraryService;

    @Inject
    @Push(channel = "notifier")
    private PushContext push;

    private Reader currentReader = new Reader();

    @Setter
    private boolean bookAvailableNotify;

    @Setter
    private Long bookId;

    public void loadReader() {
        switchReaderContext(() ->
          currentReader = of(currentReader).map(Reader::getId)
            .map(libraryService::getReader).orElse(new Reader()));
    }

    public void mergeReader() {
        libraryService.mergeReader(currentReader);
    }

    public void insertReader() {
        switchReaderContext(() -> currentReader = libraryService.insertReader(currentReader));
    }

    public void borrowBook() {
        try {
            libraryService.borrowBook(currentReader, bookId, LocalDateTime.now());
            currentReader = libraryService.getReader(currentReader.getId());
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    public void returnBook() {
        try {
            libraryService.returnBook(currentReader, bookId);
            currentReader = libraryService.getReader(currentReader.getId());
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    private void switchReaderContext(Runnable action) {
        removeReaderConsumer();
        action.run();
        registerReaderConsumer();
    }

    private void registerReaderConsumer() {
        LibraryContext.registerConsumer(
          libraryConsumer(message -> currentReader.getId() == message.getLongProperty(READER_ID))
        );
        if (bookAvailableNotify) {
            LibraryContext.registerConsumer(
              libraryConsumer(message -> message.getLongProperty(READER_ID) == 0));
        }
    }

    private LibraryConsumer libraryConsumer(LibraryConsumer.MessagePredicate shouldConsume) {
        return new LibraryConsumer(currentReader.getId(), shouldConsume,
          message -> push.send(((TextMessage) message).getText()));
    }

    @PreDestroy
    private void removeReaderConsumer() {
        LibraryContext.removeConsumer(currentReader.getId());
    }
}

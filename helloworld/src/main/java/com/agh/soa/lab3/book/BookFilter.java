package com.agh.soa.lab3.book;

import com.agh.soa.lab3.book.predicate.BookEnumPredicate;
import com.agh.soa.lab3.book.predicate.BookPredicate;
import com.agh.soa.lab3.book.predicate.BookStringPredicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Setter
@Getter
@RequiredArgsConstructor
public class BookFilter implements Predicate<Book> {

    private final boolean include;

    private String titleSearchPhrase;
    private String authorSearchPhrase;
    private BookType type;
    private Double priceFrom;
    private Double priceTo;
    private Currency currencyCode;
    private Integer pagesFrom;
    private Integer pagesTo;

    @Override
    public boolean test(Book inputBook) {
        return of(predicates())
                .filter(CollectionUtils::isNotEmpty)
                .map(predicates -> {
                    var book = ofNullable(inputBook);

                    for (var predicate : predicates)
                        book = book.filter(predicate);

                    return book.isPresent() == include;
                })
                .orElse(true);
    }

    private List<Predicate<Book>> predicates() {
        return Stream.of(
                BookStringPredicate.of(titleSearchPhrase, (book, phrase) -> book.getTitle().contains(phrase)),
                BookStringPredicate.of(authorSearchPhrase, (book, phrase) -> book.getAuthor().contains(phrase)),
                BookEnumPredicate.of(type, (book, bookType) -> bookType == book.getType()),
                BookPredicate.of(priceFrom, (book, from) -> book.getPrice() >= from),
                BookPredicate.of(priceTo, (book, to) -> to > book.getPrice()),
                BookEnumPredicate.of(currencyCode, (book, curr) -> curr == book.getCurrency()),
                BookPredicate.of(pagesFrom, (book, from) -> book.getPages() >= from),
                BookPredicate.of(pagesTo, (book, to) -> to > book.getPages())
        )
                .filter(BookPredicate::shouldTest).collect(toList());
    }
}

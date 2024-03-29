/*
 * Copyright 2020 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;

@Repository
public class BookRepository {

    private final AtomicLong sequence = new AtomicLong (0);
    private Map<Long, Book> books;

    @PostConstruct
    public void init () {
        books = Stream.of (
            new Book (getNextId (), "Domain Driven Design", singletonList (
                new Author (getNextId (), "Eric Evans")
            )),
            new Book (getNextId (), "Refactoring", singletonList (
                new Author (getNextId (), "Martin Fowler")
            )),
            new Book (getNextId (), "Clean Code", singletonList (
                new Author (getNextId (), "Robert C. Martin")
            )),
            new Book (getNextId (), "Working Effectively with Legacy Code", singletonList (
                new Author (getNextId (), "Michael C. Feathers")
            )),
            new Book (getNextId (), "The Cucumber Book", Arrays.asList (
                new Author (getNextId (), "Matt Wynne"),
                new Author (getNextId (), "Aslak Hellesøy")
            ))
        ).collect(Collectors.toConcurrentMap (Book::getId, Function.identity()));
    }

    void clear () {
        books = new ConcurrentHashMap<> ();
    }

    public Flux<Book> findAll () {
        return Flux.fromIterable (books.values ());
    }

    public Mono<Book> find (Long id) {
        Book book = books.get (id);
        if (book == null) {
            return Mono.empty ();
        }

        return Mono.just (book);
    }

    public Mono<Void> save (Flux<Book> books) {
        // does not check for duplicates...
        return books
            .map (this::assignIds)
            .doOnNext (this::addBook)
            .then ();
    }

    private void addBook (Book book) {
        books.put (book.getId (), book);
    }

    private Book assignIds (Book book) {
        book.setId (getNextId ());
        book.getAuthors ().forEach ( author ->
            author.setId (getNextId ())
        );
        return book;
    }

    private long getNextId () {
        return sequence.incrementAndGet ();
    }

}

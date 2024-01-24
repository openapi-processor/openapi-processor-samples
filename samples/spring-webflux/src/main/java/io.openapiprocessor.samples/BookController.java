/*
 * Copyright 2020 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples;


import io.openapiprocessor.openapi.api.BookApi;
import io.openapiprocessor.openapi.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * implementation of the generated BookApi.
 * uses ResponseEntity just to show it is possible ;-)
 */
@Validated
@RestController
public class BookController implements BookApi {

    private final BookRepository repository;
    private final BookMapper mapper;

    public BookController (BookRepository repository, BookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Mono<Book>> getBooksId(Long id) {
        return ResponseEntity.ok ()
            .header ("spring-webflux-in", "long")
            .header ("spring-webflux-out", "mono")
            .body (repository.find (id)
                .switchIfEmpty (Mono.error(new UnknownBookException ()))
                .map (mapper::toResource));
    }

    @Override
    public ResponseEntity<Flux<Book>> getBooks() {
        return ResponseEntity.ok ()
            .header ("spring-webflux-in", "nothing")
            .header ("spring-webflux-out", "flux")
                .body (repository.findAll ()
                .map (mapper::toResource));
    }

    @Override
    public ResponseEntity<Mono<Void>> postBooks(Flux<Book> books) {
        return ResponseEntity.status (HttpStatus.CREATED)
            .header ("spring-webflux-in", "flux")
            .header ("spring-webflux-out", "mono")
            .body (repository.save (books.map (mapper::toDomain)));
    }

}

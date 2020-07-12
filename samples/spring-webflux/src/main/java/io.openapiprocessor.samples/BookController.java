/*
 * Copyright 2020 the original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.openapiprocessor.samples;


import io.openapiprocessor.openapi.api.BookApi;
import io.openapiprocessor.openapi.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * implementation of the generated BookApi.
 *
 * uses ResponseEntity just to show it is possible ;-)
 */
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

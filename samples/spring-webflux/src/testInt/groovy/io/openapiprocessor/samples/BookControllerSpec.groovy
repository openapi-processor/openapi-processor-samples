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

package io.openapiprocessor.samples


import io.openapiprocessor.openapi.model.Book as BookResource
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Specification

@WebFluxTest (controllers = BookController)
class BookControllerSpec extends Specification {

    @SpringBean
    BookRepository repository = Mock ()

    @SpringBean
    BookMapper mapper = new BookMapperImpl ()

    @Autowired
    WebTestClient webClient

    void "get all books with existing books succeeds" () {
        Book[] books = [
            new Book (1, 'title 1', [
                new Author (2, 'author 2')
            ]),
            new Book (3, 'title 3', [
                new Author (4, 'author 4')
            ]),
            new Book (5, 'title 5', [
                new Author (6, 'author 6'),
                new Author (7, 'author 7')
            ])
        ]
        repository.findAll () >> Flux.fromArray (books)

        expect:
        def result = webClient
            .get ()
            .uri ('/books')
            .accept (MediaType.APPLICATION_JSON)
            .exchange ()
            .expectBodyList (BookResource)
            .returnResult ()
            .responseBody

        result.size () == 3
        result [2].id == 5
        result [2].authors == books [2].authors.collect { it.name } as String[]
    }

    void "get all books with no existing books succeeds" () {
        repository.findAll () >> Flux.fromArray ([] as Book[])

        expect:
        webClient
            .get ()
            .uri ('/books')
            .accept (MediaType.APPLICATION_JSON)
            .exchange ()
            .expectBodyList (BookResource)
            .hasSize (0)
    }

    void "get existing book succeeds" () {
        Book book = new Book (1, 'title 1', [
            new Author (2, 'author 2')
        ])

        repository.find (1) >> Mono.just (book)

        expect:
        webClient
            .get ()
            .uri ('/books/1')
            //.uri ({ ub -> ub.path ('/books/{id}').build (1)})
            .accept (MediaType.APPLICATION_JSON)
            .exchange ()
            .expectBody ()
            .jsonPath ('$.id').isEqualTo (book.id)
            .jsonPath ('$.title').isEqualTo (book.title)
            .jsonPath ('$.authors').isEqualTo (book.authors.collect { it.name })
    }

    void "get missing book fails with 'not found'" () {
        repository.find (1) >> Mono.empty ()

        expect:
        webClient
            .get ()
            .uri ('/books/1')
            .accept (MediaType.APPLICATION_JSON)
            .exchange ()
            .expectStatus ()
            .isNotFound ()
    }

    void "add additional books" () {
        when:
        webClient
            .post ()
            .uri ('/books')
            .contentType (MediaType.APPLICATION_JSON)
            .bodyValue ("""\
[{
    "title": "Clean Code",
    "authors": ["Robert C. Martin"]
 }, {
    "title": "Test Driven Development",
    "authors": ["Kent Beck"]
}]
""")
            .exchange ()
            .expectStatus ()
            .isCreated ()

        then:
        1 * repository.save (_) >> {
            def books = it[0] as Flux<Book>
            books.doOnNext ({ n ->
                println "${n.title}, ${n.authors.first ().name}"
            })
            .then ()
        }
    }

}

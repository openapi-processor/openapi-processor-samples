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

import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import spock.lang.Specification

class BookRepositorySpec extends Specification {

    def repository = new BookRepository()

    void setup () {
        repository.init ()
    }

    void "initializes the repository" () {
        expect:
        StepVerifier.create (repository.findAll ())
            .expectNextCount (5)
            .verifyComplete ()
    }

    void "get all existing books" () {
        expect:
        StepVerifier.create (repository.findAll ())
            .assertNext ({
                assert it.id
                assert it.title == 'Domain Driven Design'
            })
            .assertNext ({
                assert it.id
                assert it.title == 'Refactoring'
            })
            .assertNext ({
                assert it.id
                assert it.title == 'Clean Code'
            })
            .assertNext ({
                assert it.id
                assert it.title == 'Working Effectively with Legacy Code'
            })
            .assertNext ({
                assert it.id
                assert it.title == 'The Cucumber Book'
            })
            .verifyComplete ()
    }

    void "get all books handles empty repository" () {
        repository.clear ()

        expect:
        StepVerifier.create (repository.findAll ())
            .expectNextCount (0)
            .verifyComplete ()
    }

    void "get a single existing book" () {
        expect:
        StepVerifier.create (repository.find (1))
            .assertNext ({
                assert it.id == 1
                assert it.title == 'Domain Driven Design'
            })
            .verifyComplete ()
    }

    void "get a non existing book book is empty" () {
        repository.clear ()

        expect:
        StepVerifier.create (repository.find (1))
            .verifyComplete ()
    }

    void "stores new books" () {
        repository.clear ()

        Book[] books = [
            new Book (null, 'title 1', [
                new Author (null, 'author 1')
            ]),
            new Book (null, 'title 2', [
                new Author (null, 'author 2')
            ]),
            new Book (null, 'title 3', [
                new Author (null, 'author 3'),
                new Author (null, 'author 4')
            ])
        ]

        expect:
        StepVerifier.create (repository.save (Flux.fromArray (books)))
            .verifyComplete ()

        StepVerifier.create (repository.findAll ()
            .sort (new Comparator<Book> () {
                @Override
                int compare (Book o1, Book o2) {
                    o1.title <=> o2.title
                }
            }))
            .assertNext ({
                assert it.id
                assert it.title == books[0].title
                assert it.authors.size () == 1
                assert it.authors.first ().id
                assert it.authors.first ().name == books[0].authors[0].name
            })
            .assertNext ({
                assert it.id
                assert it.title == books[1].title
                assert it.authors.size () == 1
                assert it.authors.first ().id
                assert it.authors.first ().name == books[1].authors[0].name
            })
            .assertNext ({
                assert it.id
                assert it.title == books[2].title
                assert it.authors.size () == 2
                assert it.authors.first ().id
                assert it.authors[0].name == books[2].authors[0].name
                assert it.authors[1].name == books[2].authors[1].name
            })
            .verifyComplete ()

    }

}

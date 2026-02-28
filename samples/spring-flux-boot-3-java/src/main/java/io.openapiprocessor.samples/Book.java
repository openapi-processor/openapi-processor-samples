/*
 * Copyright 2020 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples;

import java.util.List;

public class Book {

    private Long id;
    private String title;
    private List<Author> authors;

    public Book () {
    }

    public Book(Long id, String title, List<Author> authors) {
        this.id = id;
        this.title = title;
        this.authors = authors;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public List<Author> getAuthors () {
        return authors;
    }

    public void setAuthors (List<Author> authors) {
        this.authors = authors;
    }

}

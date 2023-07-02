/*
 * Copyright 2020 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples;

import io.openapiprocessor.openapi.model.Book;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper (componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BookMapper {

    Book toResource (io.openapiprocessor.samples.Book source);

    default String toResource (Author source) {
        return source.getName ();
    }

    io.openapiprocessor.samples.Book toDomain (Book book);

    default List<Author> toDomain (String[] authors) {
        return Stream.of (authors)
            .map (a -> {
                Author author = new Author ();
                author.setName (a);
                return author;
            })
            .collect(Collectors.toList());
    }

}

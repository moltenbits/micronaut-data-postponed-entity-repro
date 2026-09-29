package com.example;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.soabase.recordbuilder.core.RecordBuilder;

/**
 * An entity whose static factory returns {@code WidgetBuilder}, a type
 * RecordBuilder's annotation processor generates in the same compilation.
 */
@MappedEntity
@RecordBuilder
public record Widget(@Id @GeneratedValue Long id, String name) {

    public static WidgetBuilder named(String name) {
        return WidgetBuilder.builder().name(name);
    }
}

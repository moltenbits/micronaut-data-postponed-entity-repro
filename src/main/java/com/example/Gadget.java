package com.example;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.soabase.recordbuilder.core.RecordBuilder;

/**
 * Control: the same entity, using its generated builder only inside a method
 * body, so no signature names a type generated in the same compilation.
 */
@MappedEntity
@RecordBuilder
public record Gadget(@Id @GeneratedValue Long id, String name) {

    public static Gadget named(String name) {
        return GadgetBuilder.builder().name(name).build();
    }
}

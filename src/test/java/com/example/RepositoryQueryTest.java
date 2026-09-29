package com.example;

import io.micronaut.core.io.service.SoftServiceLoader;
import io.micronaut.data.intercept.annotation.DataMethod;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.inject.BeanDefinitionReference;
import io.micronaut.inject.ExecutableMethod;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Micronaut Data implements each abstract repository method at compile time and
 * records the interceptor that runs it in the method's metadata. A method
 * compiled without it fails when called with "Micronaut Data method is missing
 * compilation time query information". These tests read the compiled metadata
 * directly, without starting an application context.
 */
class RepositoryQueryTest {

    @Test
    void repositoryOfAnEntityWhoseStaticMethodReturnsAGeneratedTypeHasItsQueries() {
        assertEquals(List.of(), methodsWithoutQueries(WidgetRepository.class));
    }

    @Test
    void repositoryOfAnEntityThatUsesItsGeneratedTypeOnlyInAMethodBodyHasItsQueries() {
        assertEquals(List.of(), methodsWithoutQueries(GadgetRepository.class));
    }

    private static List<String> methodsWithoutQueries(Class<?> repository) {
        BeanDefinition<?> definition = SoftServiceLoader.load(BeanDefinitionReference.class).collectAll().stream()
            .filter(reference -> reference.getBeanDefinitionName().startsWith("com.example.") && reference.isPresent())
            .filter(reference -> repository.isAssignableFrom(reference.getBeanType()))
            .findFirst()
            .orElseThrow()
            .load();
        return definition.getExecutableMethods().stream()
            .filter(ExecutableMethod::isAbstract)
            .filter(method -> method.classValue(DataMethod.class, DataMethod.META_MEMBER_INTERCEPTOR).isEmpty())
            .map(ExecutableMethod::getMethodName)
            .sorted()
            .toList();
    }
}

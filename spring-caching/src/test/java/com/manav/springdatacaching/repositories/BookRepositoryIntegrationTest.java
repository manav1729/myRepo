package com.manav.springdatacaching.repositories;

import com.manav.caching.boot.CacheApplication;
import com.manav.springdatacaching.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@EntityScan(basePackageClasses = Book.class)
@SpringBootTest(classes = CacheApplication.class)
@EnableJpaRepositories(basePackageClasses = BookRepository.class)
public class BookRepositoryIntegrationTest {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    BookRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(new Book(UUID.randomUUID(), "Dune"));
        repository.save(new Book(UUID.randomUUID(), "Foundation"));
    }

    @Test
    void givenBookThatShouldBeCached_whenFindByTitle_thenResultShouldBePutInCache() {
        Optional<Book> dune = repository.findFirstByTitle("Dune");

        assertEquals(dune, getCachedBook("Dune"));
    }

    @Test
    void givenBookThatShouldNotBeCached_whenFindByTitle_thenResultShouldNotBePutInCache() {
        repository.findFirstByTitle("Foundation");

        assertEquals(empty(), getCachedBook("Foundation"));
    }

    private Optional<Book> getCachedBook(String title) {
        return ofNullable(cacheManager.getCache("books")).map(c -> c.get(title, Book.class));
    }

}

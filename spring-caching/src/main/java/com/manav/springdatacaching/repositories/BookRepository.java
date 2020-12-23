package com.manav.springdatacaching.repositories;

import com.manav.springdatacaching.model.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<Book, UUID> {

    @Cacheable(value = "books", unless = "#a0=='Foundation'")
    Optional<Book> findFirstByTitle(String title);

}

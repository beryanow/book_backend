package beryanov.repository;

import beryanov.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    Optional<Book> findBookByNameAndAuthor(String name, String author);
}

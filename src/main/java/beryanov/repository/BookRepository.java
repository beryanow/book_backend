package beryanov.repository;

import beryanov.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    Optional<Book> findBookByNameAndAuthor(String name, String author);
    @Query("select book from Book book where book.toRead.flag = true")
    List<Book> findBooksToRead();
}

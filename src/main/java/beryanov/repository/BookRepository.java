package beryanov.repository;

import beryanov.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    Optional<Book> findBookByNameAndAuthor(String name, String author);

    @Query("select book from Book book where book.read.flag = true")
    List<Book> findBooksRead();

    @Query("select book from Book book where book.reading.flag = true")
    List<Book> findBooksReading();

    @Query("select book from Book book where book.toRead.flag = true")
    List<Book> findBooksToRead();

    @Query("select book from Book book where book.favourite.flag = true")
    List<Book> findBooksFavourite();
}

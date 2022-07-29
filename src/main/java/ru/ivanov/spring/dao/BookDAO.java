package ru.ivanov.spring.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ivanov.spring.models.Book;
import ru.ivanov.spring.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("Select * from Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void createBook(Book book) {
        jdbcTemplate.update("insert into Book (author, genre, title, year) values (?, ?, ?, ?)",
                book.getAuthor(), book.getGenre(), book.getTitle(), book.getYear());
    }

    public Optional<Book> showBook(String author, String genre, String title, int year) {
        List<Book> books = jdbcTemplate.query("select * from Book where author = ? and genre = ? and title = ? and year = ?",
                new BeanPropertyRowMapper<>(Book.class), author, genre, title, year);
        return books.stream().findAny();
    }

    public Book showBook(int id) {
        List<Book> books = jdbcTemplate.query("select * from Book where id = ?", new BeanPropertyRowMapper<>(Book.class), id);
        return books.stream().findAny().orElse(null);
    }

    public void updateBook(Book book) {
        jdbcTemplate.update("update Book set author = ?, genre = ?, title = ?, year = ? where id = ?",
                book.getAuthor(), book.getGenre(), book.getTitle(), book.getId());
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("delete from Book where id = ?", id);
    }

    public Optional<Person> getBookOwner(int bookId) {
        return jdbcTemplate.query("select person.* from book join person on person.id = book.person_id where book.id = ? ",
                new Object[]{bookId}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void releaseBook(int bookId) {
        jdbcTemplate.update("update book set person_id=NULL where id = ?", bookId);
    }

    public void assignBook(int bookId, Person selectedPerson) {
        jdbcTemplate.update("update book set person_id = ? where id = ?", selectedPerson.getId(), bookId);
    }
}

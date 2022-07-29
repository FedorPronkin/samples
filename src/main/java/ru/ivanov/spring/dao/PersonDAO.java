package ru.ivanov.spring.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ivanov.spring.models.Book;
import ru.ivanov.spring.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("Select * from Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void addPerson(Person person) {
        jdbcTemplate.update("insert into Person (name, age, email, address) values (?, ?, ?, ?)",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress());
    }

    public Optional<Person> showPerson(String email) {
        List<Person> people = jdbcTemplate.query("select * from Person where email = ?", new BeanPropertyRowMapper<>(Person.class), email);
        return people.stream().findAny();
    }

    public Person showPerson(int personId) {
        List<Person> people = jdbcTemplate.query("select * from Person where id = ?", new BeanPropertyRowMapper<>(Person.class), personId);
        return people.stream().findAny().orElse(null);
    }

    public void updatePerson(Person person) {
        jdbcTemplate.update("update person set name = ?, age = ?, email = ?, address = ?, where id = ?",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress(), person.getId());

    }

    public void deletePerson(int personId) {
        jdbcTemplate.update("delete from person where id = ?", personId);
    }

    public List<Book> getListOfBooksForPerson(int personId) {
        return jdbcTemplate.query("select * from book where person_id = ?", new BeanPropertyRowMapper<>(Book.class), personId);
    }
}
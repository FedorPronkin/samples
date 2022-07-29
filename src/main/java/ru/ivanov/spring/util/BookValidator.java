package ru.ivanov.spring.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ivanov.spring.dao.BookDAO;
import ru.ivanov.spring.models.Book;

@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;

    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (bookDAO.showBook(book.getAuthor(), book.getGenre(), book.getTitle(), book.getYear()).isPresent()) {
            errors.rejectValue("", "", "Such book already exists!");
        }
    }
}

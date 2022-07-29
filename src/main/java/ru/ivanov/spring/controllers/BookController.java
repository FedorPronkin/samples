package ru.ivanov.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.spring.dao.BookDAO;
import ru.ivanov.spring.dao.PersonDAO;
import ru.ivanov.spring.models.Book;
import ru.ivanov.spring.models.Person;
import ru.ivanov.spring.util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final BookValidator bookValidator;

    public BookController(PersonDAO personDAO, BookDAO bookDAO, BookValidator bookValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{bookId}")
    public String show(Model model, @ModelAttribute Person person, @PathVariable("bookId") int bookId) {

        model.addAttribute("book", bookDAO.showBook(bookId));

        Optional<Person> bookOwner = bookDAO.getBookOwner(bookId);

        if(bookOwner.isPresent()){
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people", personDAO.index());
        }

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        bookDAO.createBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{bookId}/edit")
    public String editBook(Model model, @PathVariable("bookId") int bookId) {
        model.addAttribute("book", bookDAO.showBook(bookId));
        return "books/edit";
    }

    @PatchMapping("/{bookId}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("bookId") int bookId) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.updateBook(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable("bookId") int bookId) {
        bookDAO.deleteBook(bookId);
        return "redirect:/books";
    }

    @PatchMapping("/{bookId}/release")
    public String releaseBook(@PathVariable ("bookId") int bookId){
        bookDAO.releaseBook(bookId);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/{bookId}/assign")
    public String assignBook(@PathVariable ("bookId") int bookId, @ModelAttribute("person") Person selectedPerson) {
        bookDAO.assignBook(bookId, selectedPerson);
        return "redirect:/books/" + bookId;
    }
}

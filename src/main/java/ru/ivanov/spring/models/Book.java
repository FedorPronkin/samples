package ru.ivanov.spring.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class Book {

    private int id;

    @NotEmpty
    @Size(min = 1, max = 30, message = "Wrong author`s name!")
    private String author;

    @NotEmpty
    @Size(min = 3, max = 30, message = "Wrong genre name")
    private String genre;

    @NotEmpty
    @Size(min = 2, max = 30, message = "Wrong title size!")
    private String title;

    @Min(value = 1000, message = "Год издания должен быть позже 1000")
    @Max(value = 2022, message = "Год издания должен быть раньше 1000")
    private int year;

    public Book() {
    }

    public Book(int id, String author, String genre, String title, int year) {
        this.id = id;
        this.author = author;
        this.genre = genre;
        this.title = title;
        this.year = year;
    }

    @Override
    public String toString(){
        return "Author: " + author + ", Genre: " + genre + ", Title: " + title + ", ID = " + id;
    }
}

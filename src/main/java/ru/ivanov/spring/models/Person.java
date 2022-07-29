package ru.ivanov.spring.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class Person {
    private int id;

    @NotEmpty(message = "Name can`t be empty")
    @Size(min = 2, max = 30, message = "Wrong name length!")
    private String name;

    @Min(value = 0, message = "Wrong age!")
    private int age;

    @NotEmpty(message = "E-mail can`t be empty")
    @Email(message = "Wrong e-mail!")
    @Size(min = 8, max = 50, message = "Wrong e-mail length!")
    private String email;

    // Страна, город, индекс (6 цифр)
    @NotEmpty
    @Size(min = 5, max = 50, message = "Wring address length!")
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address format is wrong (Country, City, Index{6})!")
    private String address;

    public Person(int id, String name, int age, String email, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public Person(){

    }
}

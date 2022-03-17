package com.example.CAR_RENT.entity;


import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Size(min = 10, max = 128, message = "Your message incorrect")
    private String text;

    public User getAuthor() {
        return author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

package com.example.CAR_RENT.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String carName;
    private String category;
    private String description;
    private boolean inRent;
    private Integer price;
    private String iconName;
    private Integer numberOfRents;

    @OneToMany()
    @JoinColumn(name = "car_id")
    private List<Review> reviews;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInRent() {
        return inRent;
    }

    public void setInRent(boolean inRent) {
        this.inRent = inRent;
    }

    public Integer getPriceForHour() {
        return price;
    }

    public void setPriceForHour(Integer priceForHour) {
        this.price = priceForHour;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public Integer getNumberOfRents() {
        return numberOfRents;
    }

    public void setNumberOfRents(Integer numberOfRents) {
        this.numberOfRents = numberOfRents;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}

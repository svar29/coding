package com.agoda.model;

import java.math.BigDecimal;
import java.util.Comparator;

public class Hotel {
    private Integer id;
    private String room;
    private String city;
    private BigDecimal price;
    public static Comparator<Hotel> sortByPriceASC = (hotel1, hotel2) -> hotel1.getPrice().compareTo(hotel2.getPrice());
    public static Comparator<Hotel> sortByPriceDESC = (hotel1, hotel2) -> hotel2.getPrice().compareTo(hotel1.getPrice());

    public Hotel(Integer id, String room, String city, BigDecimal price) {
        this.id = id;
        this.room = room;
        this.city = city;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getRoom() {
        return room;
    }

    public String getCity() {
        return city;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", room='" + room + '\'' +
                ", city='" + city + '\'' +
                ", price=" + price +
                '}';
    }
}

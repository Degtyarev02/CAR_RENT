package com.example.CAR_RENT;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class test {
    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.now();
        System.out.println(time);
        time = time.plusHours(1);
        System.out.println(time);
        System.out.println(LocalDateTime.parse(time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
    }
}

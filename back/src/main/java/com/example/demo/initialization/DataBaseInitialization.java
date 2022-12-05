package com.example.demo.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInitialization {

    @Autowired
    private

    @Value("classpath:data/movielist.csv")
    private Resource resource;

    public DataBaseInitialization {
    }
}

package com.example.demo.initialization;

import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.ProducerRepository;
import com.example.demo.repository.StudioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DataBaseInitializationTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private StudioRepository studioRepository;

    @Test
    public void test() {
        assertEquals(206, movieRepository.count());
        assertEquals(360, producerRepository.count());
        assertEquals(59, studioRepository.count());
    }
}

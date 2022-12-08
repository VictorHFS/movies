package com.example.demo.initialization;

import com.example.demo.domain.Movie;
import com.example.demo.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DataBaseInitializationTest {

    private MovieRepository movieRepository;

    @Test
    public void test() {
        assertEquals(0, movieRepository.count());
    }
}

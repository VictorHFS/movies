package com.example.demo.initialization;

import com.example.demo.domain.Movie;
import com.example.demo.initialization.exception.InitializationException;
import com.example.demo.initialization.factory.MovieFactory;
import com.example.demo.initialization.factory.RawMovie;
import com.example.demo.repository.MovieRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataBaseInitialization {

    @Value("classpath:data/movielist.csv")
    private Resource resource;

    @Autowired
    private MovieRepository movieRepository;

    public DataBaseInitialization()  {
        List<Movie> movies = readCsv();
        movieRepository.saveAll(movies);
    }

    private List<Movie> readCsv() {
        try {
            File file = resource.getFile();
            return new CsvToBeanBuilder<RawMovie>(new FileReader(file))
                    .build()
                    .stream()
                    .parallel()
                    .map(MovieFactory::create)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new InitializationException(e);
        }
    }
}

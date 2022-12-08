package com.example.demo.initialization;

import com.example.demo.domain.Movie;
import com.example.demo.initialization.exception.InitializationException;
import com.example.demo.initialization.factory.MovieFactory;
import com.example.demo.repository.MovieRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DataBaseInitialization {

    public DataBaseInitialization(MovieRepository movieRepository)  {
        List<Movie> movies = readCsv();
        movieRepository.saveAll(movies);
    }

    private List<Movie> readCsv() {
        try {
            File file = ResourceUtils.getFile("classpath:data/movielist.csv");
            try(var reader=  new BufferedReader(new FileReader(file))) {
                String header = reader.readLine();
                return reader.lines()
                        .parallel()
                        .map(line -> {
                            if (!header.equals(line)) {
                                String[] fields = line.split(";");
                                return Optional.of(MovieFactory.create(fields));
                            }
                            return Optional.empty();
                        }).filter(Optional::isPresent)
                        .map(o -> (Movie) o.get())
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new InitializationException(e);
        }
    }
}

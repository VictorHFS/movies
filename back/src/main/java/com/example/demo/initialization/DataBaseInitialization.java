package com.example.demo.initialization;

import com.example.demo.domain.Movie;
import com.example.demo.domain.Producer;
import com.example.demo.domain.Studio;
import com.example.demo.initialization.exception.InitializationException;
import com.example.demo.initialization.factory.MovieFactory;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.ProducerRepository;
import com.example.demo.repository.StudioRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DataBaseInitialization {

    private final MovieRepository movieRepository;
    private final ProducerRepository producerRepository;
    private final StudioRepository studioRepository;
    private final EntityManager em;

    public DataBaseInitialization(MovieRepository movieRepository,
            ProducerRepository producerRepository, StudioRepository studioRepository,
            EntityManager em)  {
        this.movieRepository = movieRepository;
        this.producerRepository = producerRepository;
        this.studioRepository = studioRepository;
        this.em = em;
        initializeData();
    }

    @Transactional
    private void initializeData() {
        List<Movie> movies = readCsv();

        Set<Producer> allProducers = movies.parallelStream()
                .map(Movie::getProducers)
                .reduce((a, b) -> {
                    HashSet<Producer> hashSet = new HashSet<>();
                    hashSet.addAll(a);
                    hashSet.addAll(b);
                    return hashSet;
                }).orElse(new HashSet<>());
        producerRepository.saveAll(allProducers);
        Set<Studio> allStudios = movies.parallelStream()
                .map(Movie::getStudios)
                .reduce((a, b) -> {
                    HashSet<Studio> hashSet = new HashSet<>();
                    hashSet.addAll(a);
                    hashSet.addAll(b);
                    return hashSet;
                }).orElse(new HashSet<>());
        studioRepository.saveAll(allStudios);

        for (Movie movie : movies) {
            List<Producer> transientProducers = movie.getProducers().parallelStream()
                    .filter(p -> Objects.isNull(p.getId()))
                    .collect(Collectors.toList());
            for (Producer transientProducer : transientProducers) {
                allProducers.parallelStream()
                        .filter(p -> p.getName().equals(transientProducer.getName()))
                        .findAny()
                        .ifPresent(p -> {
                            movie.getProducers().remove(p);
                            movie.getProducers().add(p);
                        });
            }

            List<Studio> transientStudios = movie.getStudios().parallelStream()
                    .filter(p -> Objects.isNull(p.getId()))
                    .collect(Collectors.toList());

            for (Studio transientStudio : transientStudios) {
                allStudios.parallelStream()
                        .filter(s -> s.getName().equals(transientStudio.getName()))
                        .findAny()
                        .ifPresent(s -> {
                            movie.getStudios().remove(s);
                            movie.getStudios().add(s);
                        });
            }
        }

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
                                String[] fields = line.split(";");
                                return MovieFactory.create(fields);
                        })
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new InitializationException(e);
        }
    }
}

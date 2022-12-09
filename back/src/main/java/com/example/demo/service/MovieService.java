package com.example.demo.service;

import com.example.demo.domain.Movie;
import com.example.demo.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    public Page<Movie> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Movie> findById(UUID id) {
        return repository.findById(id);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Movie save(Movie movie) {
        return repository.save(movie);
    }

    public void patchMovie(Movie moviePatched) {
        repository.save(moviePatched);
    }
}

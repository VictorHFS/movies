package com.example.demo.controller;

import com.example.demo.controller.exception.MovieNotFoundException;
import com.example.demo.domain.Movie;
import com.example.demo.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService service;
    private final ObjectMapper mapper;

    @GetMapping
    public @ResponseBody Page<Movie> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable UUID id) {
        Movie movie = service.findById(id).orElseThrow(MovieNotFoundException::new);;
        return ResponseEntity.accepted().body(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    @PostMapping
    public ResponseEntity<Movie> save(@RequestBody Movie movie) {
        return ResponseEntity.ok(service.save(movie));
    }

    @PutMapping
    public ResponseEntity<Movie> update(@RequestBody Movie movie) {
        return ResponseEntity.ok(service.save(movie));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Movie> patch(@PathVariable UUID id, @RequestBody JsonPatch patch) {
        try {
            Movie Movie = service.findById(id).orElseThrow(MovieNotFoundException::new);
            Movie MoviePatched = applyPatchToMovie(patch, Movie);
            service.patchMovie(MoviePatched);
            return ResponseEntity.ok(MoviePatched);
        } catch (JsonPatchException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (MovieNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    private Movie applyPatchToMovie(
            JsonPatch patch, Movie targetMovie) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(mapper.convertValue(targetMovie, JsonNode.class));
        return mapper.treeToValue(patched, Movie.class);
    }
}

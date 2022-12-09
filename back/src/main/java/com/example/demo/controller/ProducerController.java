package com.example.demo.controller;

import com.example.demo.controller.exception.MovieNotFoundException;
import com.example.demo.domain.IntervaloDePremios;
import com.example.demo.domain.Movie;
import com.example.demo.service.MovieService;
import com.example.demo.service.ProducerService;
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
@RequestMapping("/api/producers")
public class ProducerController {
    private final ProducerService service;

    @GetMapping("/intervalo-de-premios")
    public @ResponseBody IntervaloDePremios intervaloDePremio() {
        return service.findIntervaloDePremios();
    }

}

package com.example.demo.service;

import com.example.demo.domain.IntervaloDePremios;
import com.example.demo.domain.Producer;
import com.example.demo.handler.IntervaloHandler;
import com.example.demo.repository.ProducerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProducerService {

    private final ProducerRepository repository;
    private final IntervaloHandler handler;

    public IntervaloDePremios findIntervaloDePremios() {
        List<Producer> producers = repository.findAll();

        return producers.parallelStream()
                .map(handler::findMaiorEMenorIntervalo)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(handler::findIntervaloDePremios)
                .orElse(new IntervaloDePremios());
    }

}

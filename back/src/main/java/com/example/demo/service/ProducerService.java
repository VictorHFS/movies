package com.example.demo.service;

import com.example.demo.domain.Intervalo;
import com.example.demo.domain.IntervaloDePremios;
import com.example.demo.domain.Producer;
import com.example.demo.handler.IntervaloHandler;
import com.example.demo.repository.ProducerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProducerService {

    private final ProducerRepository repository;
    private final IntervaloHandler handler;

    public IntervaloDePremios findIntervaloDePremios() {
        List<Producer> producers = repository.findAll();

        List<List<Intervalo>> intervalos = producers.parallelStream()
                .map(handler::findMaiorEMenorIntervalo)
                .filter(a -> !a.isEmpty())
                .collect(Collectors.toList());

        List<Intervalo> maiorIntervalo = intervalos
                .parallelStream()
                .reduce(handler::getMaiorIntervalor)
                .orElse(new ArrayList<>());

        List<Intervalo> menorIntervalo = intervalos
                .parallelStream()
                .reduce(handler::getMenorIntervalor)
                .orElse(new ArrayList<>());

        return new IntervaloDePremios(
                menorIntervalo,
                maiorIntervalo
        );

    }

}

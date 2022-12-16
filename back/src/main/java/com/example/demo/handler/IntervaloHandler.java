package com.example.demo.handler;

import com.example.demo.domain.Intervalo;
import com.example.demo.domain.IntervaloDePremios;
import com.example.demo.domain.Movie;
import com.example.demo.domain.Producer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IntervaloHandler {

    public Optional<IntervaloDePremios> findMaiorEMenorIntervalo(Producer producer) {
        List<Movie> filmesPremiados = producer.getMovie()
                .parallelStream()
                .filter(Movie::getWinner)
                .sorted(Comparator.comparing(Movie::getYear))
                .collect(Collectors.toList());

        List<Intervalo> maior = new ArrayList<>();
        List<Intervalo> menor = new ArrayList<>();

        Iterator<Movie> iterator = filmesPremiados.iterator();
        if (!iterator.hasNext()) {
            return Optional.empty();
        }
        Movie previous = iterator.next();
        if (!iterator.hasNext()) {
            return Optional.empty();
        }
        while (iterator.hasNext()) {
            Movie current = iterator.next();
            int intervalo = current.getYear() - previous.getYear();
            if (isMaior(maior, intervalo)) {
                var prev = previous.getYear();
                var foll = current.getYear();
                maior.clear();
                maior.add(new Intervalo(producer, intervalo, prev, foll));
            } else if(isEqual(maior, intervalo)) {
                var prev = previous.getYear();
                var foll = current.getYear();
                maior.add(new Intervalo(producer, intervalo, prev, foll));
            }
            if (isMenor(menor, intervalo)) {
                var prev = previous.getYear();
                var foll = current.getYear();
                menor.clear();
                menor.add(new Intervalo(producer, intervalo, prev, foll));
            } else if(isEqual(menor, intervalo)) {
                var prev = previous.getYear();
                var foll = current.getYear();
                menor.add(new Intervalo(producer, intervalo, prev, foll));
            }
            previous = current;
        }

        return Optional.of(new IntervaloDePremios(maior, menor));
    }

    private boolean isMenor(List<Intervalo> menor, int intervalo) {
        return menor.stream()
                .findAny()
                .map(m -> intervalo < m.getIntervalo())
                .orElse(true);
    }

    private boolean isMaior(List<Intervalo> maior, int intervalo) {
        return maior.stream()
                .findAny()
                .map(m -> intervalo < m.getIntervalo())
                .orElse(true);
    }

    private boolean isEqual(List<Intervalo> maior, int intervalo) {
        return maior.stream()
                .findAny()
                .map(m -> intervalo == m.getIntervalo())
                .orElse(false);
    }

    public IntervaloDePremios findIntervaloDePremios(IntervaloDePremios i, IntervaloDePremios j) {
        return new IntervaloDePremios(
                findMin(i, j),
                findMax(i, j)
        );
    }

    private List<Intervalo> findMax(IntervaloDePremios i, IntervaloDePremios j) {
        if (i.getMaxInterval() > j.getMaxInterval()) {
            return i.getMax();
        } else if (i.getMaxInterval() < j.getMaxInterval()) {
            return j.getMax();
        } else {
            ArrayList<Intervalo> newMax = new ArrayList<>();
            newMax.addAll(i.getMax());
            newMax.addAll(j.getMax());
            return newMax;
        }
    }

    private List<Intervalo> findMin(IntervaloDePremios i, IntervaloDePremios j) {
        if (i.getMinInterval() > j.getMinInterval()) {
            return j.getMin();
        } else if (i.getMinInterval() < j.getMinInterval()) {
            return i.getMin();
        } else {
            ArrayList<Intervalo> newMin = new ArrayList<>();
            newMin.addAll(i.getMin());
            newMin.addAll(j.getMin());
            return newMin;
        }
    }
}

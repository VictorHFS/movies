package com.example.demo.handler;

import com.example.demo.domain.Intervalo;
import com.example.demo.domain.IntervaloDePremios;
import com.example.demo.domain.Movie;
import com.example.demo.domain.Producer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IntervaloHandlerTest {

    private IntervaloHandler handler;

    @BeforeEach
    public void setup() {
        handler = new IntervaloHandler();
    }

    @Test
    void findIntervaloDePremios() {

        IntervaloDePremios intervalo = handler.findIntervaloDePremios(
                createIntervalo(intervalo(1), intervalo(1)),
                createIntervalo(intervalo(2), intervalo(2))
        );

        assertEquals(1, intervalo.getMin().size());
        assertEquals(1, intervalo.getMin().get(0).getIntervalo());

        assertEquals(1, intervalo.getMax().size());
        assertEquals(2, intervalo.getMax().get(0).getIntervalo());

        intervalo = handler.findIntervaloDePremios(
                createIntervalo(intervalo(2), intervalo(1)),
                createIntervalo(intervalo(2), intervalo(1))
        );
        assertEquals(2, intervalo.getMin().size());
        assertEquals(1, intervalo.getMin().get(0).getIntervalo());
        assertEquals(1, intervalo.getMin().get(1).getIntervalo());

        assertEquals(2, intervalo.getMax().size());
        assertEquals(2, intervalo.getMax().get(0).getIntervalo());
        assertEquals(2, intervalo.getMax().get(1).getIntervalo());

        intervalo = handler.findIntervaloDePremios(
                createIntervalo(intervalo(2), intervalo(2)),
                createIntervalo(intervalo(1), intervalo(1))
        );
        assertEquals(1, intervalo.getMin().size());
        assertEquals(1, intervalo.getMin().get(0).getIntervalo());

        assertEquals(1, intervalo.getMax().size());
        assertEquals(2, intervalo.getMax().get(0).getIntervalo());

        intervalo = handler.findIntervaloDePremios(
                createIntervalo(intervalo(2, 2), intervalo(2)),
                createIntervalo(intervalo(1), intervalo(1, 1))
        );
        assertEquals(2, intervalo.getMin().size());
        assertEquals(1, intervalo.getMin().get(0).getIntervalo());
        assertEquals(1, intervalo.getMin().get(1).getIntervalo());

        assertEquals(2, intervalo.getMax().size());
        assertEquals(2, intervalo.getMax().get(0).getIntervalo());
        assertEquals(2, intervalo.getMax().get(1).getIntervalo());

    }

    private IntervaloDePremios createIntervalo(Intervalo maior, Intervalo menor) {
        return new IntervaloDePremios(List.of(menor), List.of(maior));
    }

    private IntervaloDePremios createIntervalo(Intervalo maior, List<Intervalo> menor) {
        return new IntervaloDePremios(menor, List.of(maior));
    }

    private IntervaloDePremios createIntervalo(List<Intervalo> maior, Intervalo menor) {
        return new IntervaloDePremios(List.of(menor), maior);
    }

    private Intervalo intervalo(int i) {
        Intervalo intervalo = new Intervalo();
        intervalo.setIntervalo(i);
        return intervalo;
    }

    private List<Intervalo> intervalo(int i, int j) {
        return List.of(
                intervalo(i),
                intervalo(j)
        );
    }

    @Test
    void findMaiorEMenorIntervalo() {
        IntervaloDePremios intervalos = handler.findMaiorEMenorIntervalo(producerUm())
                .orElseThrow(IllegalArgumentException::new);
        Intervalo maior = intervalos.getMax().get(0);
        assertEquals(5, maior.getIntervalo());

        Intervalo menor = intervalos.getMin().get(0);
        assertEquals(3, menor.getIntervalo());

        intervalos = handler.findMaiorEMenorIntervalo(producerDois())
                .orElseThrow(IllegalArgumentException::new);
        maior = intervalos.getMax().get(0);
        assertEquals(10, maior.getIntervalo());

        menor = intervalos.getMin().get(0);
        assertEquals(5, menor.getIntervalo());

        intervalos = handler.findMaiorEMenorIntervalo(producerTres())
                .orElseThrow(IllegalArgumentException::new);
        maior = intervalos.getMax().get(0);
        assertEquals(7, maior.getIntervalo());

        menor = intervalos.getMin().get(0);
        assertEquals(2, menor.getIntervalo());
    }

    private Producer producerUm() {
        Producer producer = new Producer();

        producer.setMovie(Arrays.asList(
                movie(2000, false),
                movie(2012, false),
                movie(2010, true),
                movie(2007, true),
                movie(2015, true)
        ));

        return producer;
    }

    private Producer producerDois() {
        Producer producer = new Producer();

        producer.setMovie(Arrays.asList(
                movie(2012, false),
                movie(2007, false),
                movie(2000, true),
                movie(2015, true),
                movie(2010, true)
        ));

        return producer;
    }

    private Producer producerTres() {
        Producer producer = new Producer();

        producer.setMovie(Arrays.asList(
                movie(2010, true),
                movie(2007, true),
                movie(2000, true),
                movie(2015, false),
                movie(2012, true)
        ));

        return producer;
    }

    private Movie movie(int year, Boolean winner) {
        Movie movie = new Movie();
        movie.setYear(year);
        movie.setWinner(winner);
        return movie;
    }
}

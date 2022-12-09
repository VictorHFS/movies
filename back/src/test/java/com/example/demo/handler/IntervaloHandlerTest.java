package com.example.demo.handler;

import com.example.demo.domain.Intervalo;
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
    void getMenorIntervalor() {

        ArrayList<Intervalo> menorIntervalor = handler.getMenorIntervalor(
                Arrays.asList(intervalo(0), intervalo(1)),
                Arrays.asList(intervalo(0), intervalo(2))
        );
        assertEquals(1, menorIntervalor.size());
        assertEquals(1, menorIntervalor.get(0).getIntervalo());

        menorIntervalor = handler.getMenorIntervalor(
                Arrays.asList(intervalo(0), intervalo(1)),
                Arrays.asList(intervalo(0), intervalo(1))
        );
        assertEquals(2, menorIntervalor.size());
        assertEquals(1, menorIntervalor.get(0).getIntervalo());
        assertEquals(1, menorIntervalor.get(1).getIntervalo());

        menorIntervalor = handler.getMenorIntervalor(
                Arrays.asList(intervalo(0), intervalo(2)),
                Arrays.asList(intervalo(0), intervalo(1))
        );
        assertEquals(1, menorIntervalor.size());
        assertEquals(1, menorIntervalor.get(0).getIntervalo());

    }

    @Test
    void getMaiorIntervalor() {
        ArrayList<Intervalo> maiorIntervalor = handler.getMaiorIntervalor(
                Arrays.asList(intervalo(1)),
                Arrays.asList(intervalo(2))
        );
        assertEquals(1, maiorIntervalor.size());
        assertEquals(2, maiorIntervalor.get(0).getIntervalo());

        maiorIntervalor = handler.getMaiorIntervalor(
                Arrays.asList(intervalo(2)),
                Arrays.asList(intervalo(2))
        );
        assertEquals(2, maiorIntervalor.size());
        assertEquals(2, maiorIntervalor.get(0).getIntervalo());
        assertEquals(2, maiorIntervalor.get(1).getIntervalo());

        maiorIntervalor = handler.getMaiorIntervalor(
                Arrays.asList(intervalo(2)),
                Arrays.asList(intervalo(1))
        );
        assertEquals(1, maiorIntervalor.size());
        assertEquals(2, maiorIntervalor.get(0).getIntervalo());

    }

    private Intervalo intervalo(int i) {
        Intervalo intervalo = new Intervalo();
        intervalo.setIntervalo(i);
        return intervalo;
    }

    @Test
    void findMaiorEMenorIntervalo() {
        List<Intervalo> intervalos = handler.findMaiorEMenorIntervalo(producerUm());
        Intervalo maior = intervalos.get(0);
        assertEquals(5, maior.getIntervalo());

        Intervalo menor = intervalos.get(1);
        assertEquals(3, menor.getIntervalo());

        intervalos = handler.findMaiorEMenorIntervalo(producerDois());
        maior = intervalos.get(0);
        assertEquals(10, maior.getIntervalo());

        menor = intervalos.get(1);
        assertEquals(5, menor.getIntervalo());

        intervalos = handler.findMaiorEMenorIntervalo(producerTres());
        maior = intervalos.get(0);
        assertEquals(7, maior.getIntervalo());

        menor = intervalos.get(1);
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

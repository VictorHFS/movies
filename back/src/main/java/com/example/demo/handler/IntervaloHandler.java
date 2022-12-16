package com.example.demo.handler;

import com.example.demo.domain.Intervalo;
import com.example.demo.domain.Movie;
import com.example.demo.domain.Producer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IntervaloHandler {

    public ArrayList<Intervalo> getMenorIntervalor(List<Intervalo> a, List<Intervalo> b) {
        Intervalo menorA = a.get(a.size()-1);
        Intervalo menorB = b.get(b.size()-1);

        ArrayList<Intervalo> result = new ArrayList<>();

        if (menorA.getIntervalo() < menorB.getIntervalo()) {
            result.add(menorA);
        } else if (menorA.getIntervalo() == menorB.getIntervalo()) {
            result.add(menorA);
            result.add(menorB);
        } else {
            result.add(menorB);
        }
        return result;
    }

    public ArrayList<Intervalo> getMaiorIntervalor(List<Intervalo> a, List<Intervalo> b) {

        Intervalo maiorA = a.get(0);
        Intervalo maiorB = b.get(0);

        ArrayList<Intervalo> result = new ArrayList<>();

        if (maiorA.getIntervalo() > maiorB.getIntervalo()) {
            result.add(maiorA);
        } else if (maiorA.getIntervalo() == maiorB.getIntervalo()) {
            result.add(maiorA);
            result.add(maiorB);
        } else {
            result.add(maiorB);
        }
        return result;
    }

    public List<Intervalo> findMaiorEMenorIntervalo(Producer producer) {
        List<Movie> filmesPremiados = producer.getMovie()
                .parallelStream()
                .filter(Movie::getWinner)
                .sorted(Comparator.comparing(Movie::getYear))
                .collect(Collectors.toList());

        int maior = -Integer.MAX_VALUE;
        int maiorPrev = -Integer.MAX_VALUE;
        int maiorFoll = -Integer.MAX_VALUE;

        int menor = Integer.MAX_VALUE;
        int menorPrev = Integer.MAX_VALUE;
        int menorFoll = Integer.MAX_VALUE;

        Iterator<Movie> iterator = filmesPremiados.iterator();
        if (!iterator.hasNext()) {
            return new ArrayList<>();
        }
        Movie previous = iterator.next();
        if (!iterator.hasNext()) {
            return new ArrayList<>();
        }
        while (iterator.hasNext()) {
            Movie current = iterator.next();
            int intervalo = current.getYear() - previous.getYear();
            if (intervalo > maior) {
                maior = intervalo;
                maiorPrev = previous.getYear();
                maiorFoll = current.getYear();
            }
            if (intervalo < menor) {
                menor = intervalo;
                menorPrev = previous.getYear();
                menorFoll = current.getYear();
            }
            previous = current;
        }

        Intervalo maiorIntervalo = new Intervalo(producer, maior, maiorPrev, maiorFoll);
        Intervalo menorIntervalo = new Intervalo(producer, menor, menorPrev, menorFoll);

        return Arrays.asList(
                maiorIntervalo,
                menorIntervalo
        );
    }
}

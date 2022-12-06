package com.example.demo.initialization.factory;

import com.example.demo.domain.Movie;

import java.util.Arrays;

public final class MovieFactory {
    public static Movie create(RawMovie raw) {
        return new Movie(
                raw.year,
                raw.title,
                Arrays.asList(raw.producers.split(";")),
                Arrays.asList(raw.studios.split(";")),
                "yes".equals(raw.winner.toLowerCase())
        );
    }
}

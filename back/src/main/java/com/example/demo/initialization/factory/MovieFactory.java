package com.example.demo.initialization.factory;

import com.example.demo.domain.Movie;
import org.springframework.util.StreamUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class MovieFactory {

    private static List<String> toStudios(String value) {
        return Optional.ofNullable(value).map(_value -> Arrays.asList(_value.split(",|( and )"))).orElse(new ArrayList<>());
    }

    private static List<String> toProducers(String value) {
        return Optional.ofNullable(value).map(_value -> Arrays.asList(_value.split(",|( and )"))).orElse(new ArrayList<>());
    }

    private static Boolean toWinner(String value) {
        return Optional.ofNullable(value).map(String::toLowerCase).map("yes"::equals).orElse(false);
    }

    public static Movie create(String[] fields) {
       return new Movie(
               Integer.valueOf(fields[0]),
               getOrNull(fields,1),
               toStudios(getOrNull(fields,2)),
               toProducers(getOrNull(fields,3)),
               toWinner(getOrNull(fields,4))
       );
    }

    private static String getOrNull(String[] arr, int index) {
        if (arr.length > index) {
            return arr[index];
        }
        return null;
    }
}

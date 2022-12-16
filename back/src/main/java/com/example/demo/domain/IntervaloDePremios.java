package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntervaloDePremios {
    List<Intervalo> min = new ArrayList<>();
    List<Intervalo> max = new ArrayList<>();

    @JsonIgnore
    public int getMaxInterval() {
        return max.stream().findAny()
                .map(Intervalo::getIntervalo)
                .orElse(-1);
    }
    @JsonIgnore
    public int getMinInterval() {
        return min.stream().findAny()
                .map(Intervalo::getIntervalo)
                .orElse(-1);
    }
}

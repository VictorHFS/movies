package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Entity
public class Movie {

    @Id
    private UUID id;
    private String title;
    private List<String> studios;
    private List<String> producers;
    private Boolean winner;

    public Movie(Integer year, String title, List<String> studios, List<String> producers, Boolean winner) {
        this.id = UUID.randomUUID();
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public UUID getId() {
        return id;
    }

    private Integer year;

    public Integer getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getStudios() {
        return studios;
    }

    public List<String> getProducers() {
        return producers;
    }

    public Boolean getWinner() {
        return winner;
    }
}

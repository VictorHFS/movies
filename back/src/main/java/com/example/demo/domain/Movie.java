package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"year", "title"})
@EqualsAndHashCode(of = {"year", "title"})
public class Movie {

    @Id
    @Column(columnDefinition = "uuid")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @Column(name = "`year`")
    private Integer year;
    private String title;

    @ManyToMany
    @JoinTable(
            name = "movie_studio",
            joinColumns = { @JoinColumn(name = "movie_id") },
            inverseJoinColumns = { @JoinColumn(name = "studio_id") }
    )
    private Set<Studio> studios;

    @ManyToMany
    @JoinTable(
            name = "movie_producer",
            joinColumns = { @JoinColumn(name = "movie_id") },
            inverseJoinColumns = { @JoinColumn(name = "producer_id") }
    )
    private Set<Producer> producers;
    private Boolean winner;

    public Movie(Integer year, String title, List<String> studios, List<String> producers, Boolean winner) {
        this.year = year;
        this.title = title.trim();
        this.studios = studios.stream().map(Studio::new).collect(Collectors.toSet());
        this.producers = producers.stream().map(Producer::new).collect(Collectors.toSet());
        this.winner = winner;
    }
}

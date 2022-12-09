package com.example.demo.controller;

import com.example.demo.MovieApplication;
import com.example.demo.domain.Movie;
import com.example.demo.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.RemoveOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MovieApplication.class)
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MovieRepository repository;

    @Test
    public void findAll() throws Exception {
        this.mock
                .perform(MockMvcRequestBuilders.get("/api/movies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content", hasSize(20)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findById() throws Exception {
        Movie movie = repository.findAll(PageRequest.of(0, 1))
                .getContent()
                .get(0);
        this.mock
                .perform(MockMvcRequestBuilders.get(String.format("/api/movies/%s", movie.getId())))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("id", equalTo(movie.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("year", equalTo(movie.getYear())))
                .andExpect(MockMvcResultMatchers.jsonPath("title", equalTo(movie.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("winner", equalTo(movie.getWinner())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteById() throws Exception {
        Movie movie = repository.findAll(PageRequest.of(0, 1))
                .getContent()
                .get(0);
        this.mock
                .perform(MockMvcRequestBuilders.delete(String.format("/api/movies/%s", movie.getId())))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect((result) -> {
                    Optional<Movie> byId = repository.findById(movie.getId());
                    assertFalse(byId.isPresent(), "Não deveria existir o registro no banco");
                })
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void save() throws Exception {
        Movie movie = new Movie(2020, "test title", Arrays.asList("test studio"), Arrays.asList("test producer"), true);

        this.mock
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/movies")
                                .content(mapper.writeValueAsString(movie))
                                .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("year", equalTo(2020)))
                .andExpect(MockMvcResultMatchers.jsonPath("title", equalTo("test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("winner", equalTo(true)))
                .andExpect((result) -> {
                    String id = mapper.readTree(result.getResponse().getContentAsString())
                            .get("id")
                            .textValue();
                    UUID movieId = UUID.fromString(id);
                    Optional<Movie> byId = repository.findById(movieId);
                    assertTrue(byId.isPresent(), "Deveria existir o registro no banco");

                    Movie m = byId.get();
                    assertEquals(2020, m.getYear());
                    assertEquals("test title", m.getTitle());
                    assertEquals("test studio", new ArrayList<>(m.getStudios()).get(0).getName());
                    assertEquals("test producer", new ArrayList<>(m.getProducers()).get(0).getName());
                    assertEquals(true, m.getWinner());
                })
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void update() throws Exception {
        Movie movie = repository.findAll(PageRequest.of(0, 1)).getContent().get(0);
        movie.setTitle("test title");

        this.mock
                .perform(
                        MockMvcRequestBuilders
                                .put("/api/movies")
                                .content(mapper.writeValueAsString(movie))
                                .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id", equalTo(movie.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("title", equalTo("test title")))
                .andExpect((result) -> {
                    Optional<Movie> byId = repository.findById(movie.getId());
                    assertTrue(byId.isPresent(), "Deveria existir o registro no banco");

                    Movie m = byId.get();
                    assertEquals("test title", m.getTitle());
                })
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void patch() throws Exception {

        Movie movie = repository.findAll(PageRequest.of(0, 1)).getContent().get(0);

        JsonPatch jsonPatch = new JsonPatch(Arrays.asList(
                new RemoveOperation(new JsonPointer("/year")),
                new ReplaceOperation(new JsonPointer("/title"), JsonNodeFactory.instance.textNode("test title"))
        ));

        this.mock
                .perform(
                        MockMvcRequestBuilders
                                .patch(String.format("/api/movies/%s", movie.getId()))
                                .content(mapper.writeValueAsString(jsonPatch))
                                .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id", equalTo(movie.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("title", equalTo("test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("year", nullValue()))
                .andExpect((result) -> {
                    Optional<Movie> byId = repository.findById(movie.getId());
                    assertTrue(byId.isPresent(), "Deveria existir o registro no banco");

                    Movie m = byId.get();
                    assertEquals("test title", m.getTitle());
                    assertNull(m.getYear());
                })
                .andDo(MockMvcResultHandlers.print());
    }
}

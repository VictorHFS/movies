package com.example.demo.controller;

import com.example.demo.MovieApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MovieApplication.class)
@AutoConfigureMockMvc
public class ProducerControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void findIntervaloDePremios() throws Exception {
        this.mock
            .perform(MockMvcRequestBuilders.get("/api/producers/intervalo-de-premios"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("min", hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("min[0].producer.name", equalTo("Joel Silver")))
            .andExpect(MockMvcResultMatchers.jsonPath("min[0].intervalo", equalTo(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("min[0].previousWin", equalTo(1990)))
            .andExpect(MockMvcResultMatchers.jsonPath("min[0].followingWin", equalTo(1991)))
            .andExpect(MockMvcResultMatchers.jsonPath("max", hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("max[0].producer.name", equalTo("Matthew Vaughn")))
            .andExpect(MockMvcResultMatchers.jsonPath("max[0].intervalo", equalTo(13)))
            .andExpect(MockMvcResultMatchers.jsonPath("max[0].previousWin", equalTo(2002)))
            .andExpect(MockMvcResultMatchers.jsonPath("max[0].followingWin", equalTo(2015)))
            .andDo(MockMvcResultHandlers.print());
    }
}

package com.example.backendapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TodoApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST then GET by id")
    void createThenGet() throws Exception {
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Study\",\"description\":\"Read spring docs\",\"status\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Study")))
                .andExpect(jsonPath("$.description", is("Read spring docs")))
                .andExpect(jsonPath("$.status", is(false)));

        mockMvc.perform(get("/api/todo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Study")));
    }

    @Test
    @DisplayName("PUT updates existing todo")
    void updateTodo() throws Exception {
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Study\",\"description\":\"Read spring docs\",\"status\":false}"))
                .andExpect(status().isOk());

        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Study hard\",\"description\":\"Read and practice\",\"status\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Study hard")))
                .andExpect(jsonPath("$.description", is("Read and practice")))
                .andExpect(jsonPath("$.status", is(true)));
    }

    @Test
    @DisplayName("DELETE removes todo")
    void deleteTodo() throws Exception {
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Study\",\"description\":\"Read spring docs\",\"status\":false}"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/todo/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET missing todo returns 404")
    void getMissingTodo() throws Exception {
        mockMvc.perform(get("/api/todo/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Validation error returns 400")
    void validationError() throws Exception {
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\",\"description\":\"\",\"status\":false}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Validation failed")))
                .andExpect(jsonPath("$.errors", hasSize(greaterThan(0))));
    }
}


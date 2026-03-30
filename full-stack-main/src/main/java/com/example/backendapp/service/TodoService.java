package com.example.backendapp.service;

import com.example.backendapp.model.Todo;
import com.example.backendapp.web.TodoCreateUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TodoService {

    // In-memory storage only (no DB), as requested.
    private final ArrayList<Todo> todos = new ArrayList<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public Todo getById(int id) {
        synchronized (todos) {
            return todos.stream()
                    .filter(t -> t.getId() != null && t.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Todo not found with id=" + id
                    ));
        }
    }

    public Todo create(TodoCreateUpdateRequest request) {
        Todo todo = new Todo(
                nextId.getAndIncrement(),
                request.getTitle(),
                request.getDescription(),
                request.isStatus()
        );

        synchronized (todos) {
            todos.add(todo);
        }
        return todo;
    }

    public Todo update(int id, TodoCreateUpdateRequest request) {
        synchronized (todos) {
            Todo existing = todos.stream()
                    .filter(t -> t.getId() != null && t.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Todo not found with id=" + id
                    ));

            existing.setTitle(request.getTitle());
            existing.setDescription(request.getDescription());
            existing.setStatus(request.isStatus());
            return existing;
        }
    }

    public void delete(int id) {
        synchronized (todos) {
            boolean removed = todos.removeIf(t -> t.getId() != null && t.getId() == id);
            if (!removed) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found with id=" + id);
            }
        }
    }
}


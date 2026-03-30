package com.example.backendapp.controller;

import com.example.backendapp.model.Todo;
import com.example.backendapp.service.TodoService;
import com.example.backendapp.web.TodoCreateUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // GET: /api/todo/{id}
    @GetMapping("/todo/{id}")
    public Todo getTodo(@PathVariable int id) {
        return todoService.getById(id);
    }

    // POST: /api/todos
    @PostMapping("/todos")
    public Todo createTodo(@RequestBody TodoCreateUpdateRequest request) {
        return todoService.create(request);
    }

    // PUT: /api/todos/{id}
    @PutMapping("/todos/{id}")
    public Todo updateTodo(@PathVariable int id, @RequestBody TodoCreateUpdateRequest request) {
        return todoService.update(id, request);
    }

    // DELETE: /api/todos/{id}
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable int id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


package com.example.backendapp.web;

import jakarta.validation.constraints.NotBlank;

/**
 * Request body for creating/updating a Todo.
 * Uses the same fields as the schema, except {@code id} (server assigns/targets it).
 */
public class TodoCreateUpdateRequest {
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "description is required")
    private String description;

    private boolean status;

    public TodoCreateUpdateRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}


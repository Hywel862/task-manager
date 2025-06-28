package com.example.task.models;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
    @NotBlank(message = "Phrase must not be blank")
    private String phrase;
}

package com.javarush.levchuk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quest implements Identifiable {
    private Long id;
    private String title;
    private final Map<Question, List<Answer>> questions = new HashMap<>();
    private Question firstQuestion;
    private User author;
}
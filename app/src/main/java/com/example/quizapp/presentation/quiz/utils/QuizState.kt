package com.example.quizapp.presentation.quiz.utils

import com.example.quizapp.data.dto.Question

data class QuizState(
    val isLoading: Boolean = false,
    val quiz: List<Question> = emptyList(),
)
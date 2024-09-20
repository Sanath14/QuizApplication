package com.example.quizapp.presentation.quiz.utils

import com.example.quizapp.data.dto.Question

sealed class QuizScreenEvent {
    data class OnQuestionClick(val question: Question) : QuizScreenEvent()
}
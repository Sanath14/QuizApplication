package com.example.quizapp.presentation.question_details.utils

sealed class QuestionDetailsScreenEvent {
    data class OnOptionClick(val selectedAnswer: String) : QuestionDetailsScreenEvent()

}
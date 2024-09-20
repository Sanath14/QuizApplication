package com.example.quizapp.presentation

import com.example.quizapp.utils.Config.QUESTION_DETAILS_SCREEN_ROUTE
import com.example.quizapp.utils.Config.QUIZ_SCREEN_ROUTE

sealed class Screen(val route: String) {
    data object QuizScreen : Screen(QUIZ_SCREEN_ROUTE)
    data object QuestionDetailsScreen : Screen(QUESTION_DETAILS_SCREEN_ROUTE)
}
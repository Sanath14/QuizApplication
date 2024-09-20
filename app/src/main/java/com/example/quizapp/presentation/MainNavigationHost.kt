package com.example.quizapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quizapp.presentation.question_details.QuestionDetailsScreen
import com.example.quizapp.presentation.quiz.QuizScreen
import com.example.quizapp.utils.Config.PARAM_QUESTION_DETAILS

@Composable
fun MainNavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.QuizScreen.route
    ) {
        composable(
            route = Screen.QuizScreen.route
        ) {
            QuizScreen(navController)
        }
        composable(
            route = Screen.QuestionDetailsScreen.route + "/{${PARAM_QUESTION_DETAILS}}"
        ) {
            QuestionDetailsScreen()
        }
    }
}
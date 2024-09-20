package com.example.quizapp.presentation.quiz

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quizapp.presentation.quiz.component.QuizItem
import com.example.quizapp.presentation.quiz.utils.QuizScreenEvent

@Composable
fun QuizScreen(
    navController: NavController,
    viewModel: QuizViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.quiz) { question ->
                QuizItem(
                    question = question,
                    onItemClick = { que ->
                        viewModel.onEvent(QuizScreenEvent.OnQuestionClick(que))
                    }
                )
            }
        }

    }
    LaunchedEffect(key1 = Unit) {
        viewModel.quizEvent.collect { event ->
            when (event) {
                is QuizViewModel.QuizEvent.NavigateToRoute -> {
                    navController.navigate(event.route)
                }

                is QuizViewModel.QuizEvent.ShowErrMsg -> {
                    Toast.makeText(context, event.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
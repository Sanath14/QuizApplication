package com.example.quizapp.presentation.question_details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.presentation.question_details.component.QuestionDetails
import com.example.quizapp.presentation.question_details.utils.CustomSnackBarVisuals
import com.example.quizapp.presentation.question_details.utils.QuestionDetailsScreenEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QuestionDetailsScreen(
    viewModel: QuestionDetailsViewModel = hiltViewModel(),
) {

    val questionDetails = viewModel.questionDetails
    val snackBarHostState = remember { SnackbarHostState() }

    var selectedAnswerValue by remember { mutableStateOf(viewModel.selectedAnswer.value) }

    val isSelectedItem: (String) -> Boolean = {
        selectedAnswerValue == it
    }
    val onSelectionChangeState: (String) -> Unit = {
        selectedAnswerValue = it
        snackBarHostState.currentSnackbarData?.dismiss()
        viewModel.onUiEvent(QuestionDetailsScreenEvent.OnOptionClick(it))
    }


    Surface {
        Scaffold(snackbarHost = {
            SnackbarHost(hostState = snackBarHostState, snackbar = { snackBarData: SnackbarData ->
                val customVisuals = snackBarData.visuals as? CustomSnackBarVisuals
                if (customVisuals != null) {
                    Snackbar(
                        snackbarData = snackBarData,
                        contentColor = Color.White,
                        containerColor = customVisuals.containerColor,
                    )
                } else {
                    Snackbar(snackbarData = snackBarData)
                }
            })
        }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                QuestionDetails(
                    questionDetails,
                    Modifier.align(Alignment.Center),
                    isSelectedItem = {
                        isSelectedItem(it)
                    },
                    onSelectionChangeState = {
                        onSelectionChangeState(it)
                    })
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.questionDetailsEvent.collect { event ->
            when (event) {
                is QuestionDetailsViewModel.QuestionDetailsEvent.ShowResultSnackBar -> {
                    snackBarHostState.showSnackbar(
                        CustomSnackBarVisuals(
                            message = event.msg, containerColor = event.color
                        )
                    )
                }
            }
        }
    }
}

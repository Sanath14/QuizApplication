package com.example.quizapp.presentation.question_details

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.dto.Question
import com.example.quizapp.presentation.question_details.utils.QuestionDetailsScreenEvent
import com.example.quizapp.utils.Config.PARAM_QUESTION_DETAILS
import com.example.quizapp.utils.fromJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuestionDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val questionDetails =
        savedStateHandle.get<String>(PARAM_QUESTION_DETAILS)?.fromJson(Question::class.java)

    private val _selectedAnswer = MutableStateFlow("")
    val selectedAnswer = _selectedAnswer.asStateFlow()

    private val _questionDetailsEventChannel = Channel<QuestionDetailsEvent>()
    val questionDetailsEvent = _questionDetailsEventChannel.receiveAsFlow()

    fun onUiEvent(event: QuestionDetailsScreenEvent) = viewModelScope.launch {
        when (event) {
            is QuestionDetailsScreenEvent.OnOptionClick -> {
                _selectedAnswer.emit(event.selectedAnswer)
                val isCorrect = event.selectedAnswer == questionDetails?.correctAnswer
                if (isCorrect) {
                    _questionDetailsEventChannel.send(
                        QuestionDetailsEvent.ShowResultSnackBar(
                            "Correct Answer",
                            Color.Green
                        )
                    )

                } else {
                    _questionDetailsEventChannel.send(
                        QuestionDetailsEvent.ShowResultSnackBar(
                            "Wrong Answer",
                            Color.Red
                        )
                    )

                }
            }
        }
    }

    sealed class QuestionDetailsEvent {
        data class ShowResultSnackBar(val msg: String, val color: Color) : QuestionDetailsEvent()
    }
}
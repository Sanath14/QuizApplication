package com.example.quizapp.presentation.quiz

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.domain.use_case.GetQuizUseCase
import com.example.quizapp.presentation.Screen
import com.example.quizapp.presentation.quiz.utils.QuizScreenEvent
import com.example.quizapp.presentation.quiz.utils.QuizState
import com.example.quizapp.utils.Resource
import com.example.quizapp.utils.toJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizUseCase: GetQuizUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    private val _quizEventChannel = Channel<QuizEvent>()
    val quizEvent = _quizEventChannel.receiveAsFlow()

    init {
        getQuiz()
    }

    private fun getQuiz() {
        getQuizUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = QuizState(quiz = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    result.message?.let { QuizEvent.ShowErrMsg(it) }
                        ?.let { _quizEventChannel.send(it) }
                }

                is Resource.Loading -> {
                    _state.value = QuizState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: QuizScreenEvent) = viewModelScope.launch {
        when (event) {
            is QuizScreenEvent.OnQuestionClick -> {
                val encodedQue = Uri.encode(event.question.toJson())
                _quizEventChannel.send(QuizEvent.NavigateToRoute(Screen.QuestionDetailsScreen.route + "/" + encodedQue))
            }
        }
    }


    sealed class QuizEvent {
        data class NavigateToRoute(val route: String) : QuizEvent()
        data class ShowErrMsg(val msg: String) : QuizEvent()
    }
}
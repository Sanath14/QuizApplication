package com.example.quizapp.domain.use_case

import com.example.quizapp.data.dto.Question
import com.example.quizapp.domain.repository.QuizRepository
import com.example.quizapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetQuizUseCase(private val repository: QuizRepository) {

    operator fun invoke(): Flow<Resource<List<Question>>> = flow {
        try {
            emit(Resource.Loading())
            val quiz = repository.getQuestions()
            emit(Resource.Success(quiz))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
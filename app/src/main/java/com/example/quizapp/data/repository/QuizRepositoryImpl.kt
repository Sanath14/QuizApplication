package com.example.quizapp.data.repository

import com.example.quizapp.data.api.QuizApi
import com.example.quizapp.domain.repository.QuizRepository

class QuizRepositoryImpl(private val api: QuizApi) : QuizRepository {

    override suspend fun getQuestions() = api.getQuestions()

}
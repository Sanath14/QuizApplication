package com.example.quizapp.data.api

import com.example.quizapp.data.dto.Question
import retrofit2.http.GET

interface QuizApi {

    @GET("avatar/questions")
    suspend fun getQuestions(): List<Question>

}
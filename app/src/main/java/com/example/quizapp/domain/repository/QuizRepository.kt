package com.example.quizapp.domain.repository

import com.example.quizapp.data.dto.Question

interface QuizRepository {
    suspend fun getQuestions(): List<Question>
}
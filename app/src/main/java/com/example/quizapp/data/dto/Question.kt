package com.example.quizapp.data.dto

import com.google.gson.annotations.SerializedName


data class Question(
    val id: Int,
    val question: String,
    @SerializedName("possibleAnsers")
    val possibleAnswers: ArrayList<String>,
    val correctAnswer: String
)

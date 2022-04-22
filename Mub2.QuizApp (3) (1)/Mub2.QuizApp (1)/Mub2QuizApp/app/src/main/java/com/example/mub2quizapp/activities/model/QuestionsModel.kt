package com.example.mub2quizapp.activities.model

import java.util.*


class QuestionsModel(
    var id: Int = getAutoId(),
    var bankId: Int = 0,
    var question: String = "",
    var correctAnswer: Int = 0,
    var answer1: String = "",
    var answer2: String = "",
    var answer3: String = "",
    var answer4: String = ""
) {
    companion object {
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(Int.MAX_VALUE)
        }
    }


}

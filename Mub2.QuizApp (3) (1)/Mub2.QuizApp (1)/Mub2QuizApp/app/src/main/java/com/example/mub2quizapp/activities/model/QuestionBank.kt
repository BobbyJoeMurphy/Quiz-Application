package com.example.mub2quizapp.activities.model

import java.util.*

data class QuestionBank(
    var id: Int = getAutoId(),
    var name: String = ""
) {
    companion object {
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(Int.MAX_VALUE)
        }
    }
}
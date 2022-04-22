package com.example.mub2quizapp.activities.repositories

import com.example.mub2quizapp.activities.model.QuestionBank
import com.example.mub2quizapp.activities.model.QuestionsModel

interface MainRepository {

    fun deleteQuestionById(questionId: Int): Boolean
    fun insertQuestions(question: QuestionsModel): Boolean
    fun getQuestionsByBankId(bankId: Int): ArrayList<QuestionsModel>

    fun getAllQuestionBanks(): ArrayList<QuestionBank>
    fun insertQuestionBank(questionBank: QuestionBank): Boolean
    fun deleteQuestionBankById(bankId: Int): Boolean
    fun getQuestionBankById(bankId: Int): Pair<Boolean, QuestionBank>
}
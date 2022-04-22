package com.example.mub2quizapp.activities.viewModels

import androidx.lifecycle.ViewModel
import com.example.mub2quizapp.activities.model.QuestionBank
import com.example.mub2quizapp.activities.model.QuestionsModel
import com.example.mub2quizapp.activities.model.SQLiteHelper
import com.example.mub2quizapp.activities.repositories.MainRepository

class MainViewModel(private val sqliteHelper: SQLiteHelper) : ViewModel(), MainRepository {

    override fun getAllQuestionBanks(): ArrayList<QuestionBank> {
        return sqliteHelper.getAllQuestionBanks() ?: arrayListOf()
    }

    override fun deleteQuestionById(questionId: Int): Boolean {
        return sqliteHelper.deleteQuestionById(questionId) != 0
    }

    override fun getQuestionsByBankId(bankId: Int): ArrayList<QuestionsModel> {
        return sqliteHelper.getQuestionsByBankId(bankId) ?: arrayListOf()
    }

    override fun insertQuestions(question: QuestionsModel): Boolean {
        return sqliteHelper.insertQuestions(question) != -1L
    }

    override fun deleteQuestionBankById(bankId: Int): Boolean {
        return sqliteHelper.deleteQuestionBankById(bankId) != 0
    }

    override fun insertQuestionBank(questionBank: QuestionBank): Boolean {
        return sqliteHelper.insertQuestionBank(questionBank) != -1L
    }

    override fun getQuestionBankById(bankId: Int): Pair<Boolean, QuestionBank> {
        val questionBank = sqliteHelper.getQuestionBankById(bankId)
        return Pair(questionBank != null, questionBank ?: QuestionBank())
    }
}
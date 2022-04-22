package com.example.mub2quizapp.viewModels

import com.example.mub2quizapp.activities.model.QuestionBank
import com.example.mub2quizapp.activities.model.QuestionsModel
import com.example.mub2quizapp.activities.model.SQLiteHelper
import com.example.mub2quizapp.activities.repositories.MainRepository
import com.example.mub2quizapp.activities.viewModels.MainViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private lateinit var mainRepository: MainRepository

    @Mock
    private lateinit var sqLiteHelper: SQLiteHelper

    companion object {
        @BeforeClass
        @JvmStatic
        fun init() {
            MockitoAnnotations.openMocks(this)
        }
    }

    @Before
    fun `before each test`() {
        mainRepository = MainViewModel(sqLiteHelper)
    }

    @Test
    fun validGetAllQuestionBanks_successReturned() {
        val questionBank = Mockito.mock(QuestionBank::class.java)
        val mockedReturn = arrayListOf<QuestionBank>().apply { add(questionBank) }
        Mockito.`when`(sqLiteHelper.getAllQuestionBanks())
            .thenReturn(mockedReturn)
        mainRepository.getAllQuestionBanks()
        assertEquals(true, mainRepository.getAllQuestionBanks().size > 0)
    }

    @Test
    fun nullGetAllQuestionBanks_emptyReturned() {
        val mockedReturn = null
        Mockito.`when`(sqLiteHelper.getAllQuestionBanks())
            .thenReturn(mockedReturn)
        mainRepository.getAllQuestionBanks()
        assertEquals(true, mainRepository.getAllQuestionBanks().isEmpty())
    }

    @Test
    fun itemsAffectedDeleteQuestionById_trueReturned() {
        val mockedReturn = 10
        Mockito.`when`(sqLiteHelper.deleteQuestionById(Mockito.anyInt()))
            .thenReturn(mockedReturn)
        mainRepository.deleteQuestionById(Mockito.anyInt())
        assertEquals(true, mainRepository.deleteQuestionById(Mockito.anyInt()))
    }

    @Test
    fun noItemsAffectedDeleteQuestionById_falseReturned() {
        val mockedReturn = 0
        Mockito.`when`(sqLiteHelper.deleteQuestionById(Mockito.anyInt()))
            .thenReturn(mockedReturn)
        mainRepository.deleteQuestionById(Mockito.anyInt())
        assertEquals(false, mainRepository.deleteQuestionById(Mockito.anyInt()))
    }

    @Test
    fun validGetQuestionsByBankId_successReturned() {
        val question = Mockito.mock(QuestionsModel::class.java)
        val mockedReturn = arrayListOf<QuestionsModel>().apply { add(question) }
        Mockito.`when`(sqLiteHelper.getQuestionsByBankId(Mockito.anyInt()))
            .thenReturn(mockedReturn)
        mainRepository.getQuestionsByBankId(Mockito.anyInt())
        assertEquals(true, mainRepository.getQuestionsByBankId(Mockito.anyInt()).size > 0)
    }

    @Test
    fun nullGetQuestionsByBankId_emptyReturned() {
        val mockedReturn = null
        Mockito.`when`(sqLiteHelper.getQuestionsByBankId(Mockito.anyInt()))
            .thenReturn(mockedReturn)
        mainRepository.getQuestionsByBankId(Mockito.anyInt())
        assertEquals(true, mainRepository.getQuestionsByBankId(Mockito.anyInt()).isEmpty())
    }

}
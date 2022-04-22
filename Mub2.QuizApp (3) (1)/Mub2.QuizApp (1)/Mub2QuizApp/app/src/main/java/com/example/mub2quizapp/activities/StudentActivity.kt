package com.example.mub2quizapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mub2quizapp.R
import com.example.mub2quizapp.activities.fragments.QuestionBankAdapter
import com.example.mub2quizapp.activities.viewModels.MainViewModel
import com.example.mub2quizapp.activities.viewModels.MainViewModelFactory

class StudentActivity : AppCompatActivity() {

    private lateinit var rcvBanks: RecyclerView

    private lateinit var factory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        factory = MainViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setContentView(R.layout.activity_student)
        rcvBanks = findViewById(R.id.rcvBanks)
        setupQuestionBankList()
    }

    private fun setupQuestionBankList() {
        val bankList = viewModel.getAllQuestionBanks()
        rcvBanks.adapter = QuestionBankAdapter(bankList, true, onCardClick = {
            Intent(this, QuizActivity::class.java).apply {
                putExtra(EXTRA_QUESTION_BANK_ID, it)
                startActivity(this)
            }
        }, onDeleteBankClick = {})
    }
}


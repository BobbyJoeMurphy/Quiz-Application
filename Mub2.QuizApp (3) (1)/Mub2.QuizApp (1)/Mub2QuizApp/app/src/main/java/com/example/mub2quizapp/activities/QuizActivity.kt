package com.example.mub2quizapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mub2quizapp.R
import com.example.mub2quizapp.activities.model.QuestionsModel
import com.example.mub2quizapp.activities.model.SQLiteHelper
import com.example.mub2quizapp.activities.viewModels.MainViewModel
import com.example.mub2quizapp.activities.viewModels.MainViewModelFactory
import com.example.mub2quizapp.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    private val questionBankId by lazy { intent.getIntExtra(EXTRA_QUESTION_BANK_ID, -1) }

    private lateinit var factory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityQuizBinding

    private var questionList = arrayListOf<QuestionsModel>()
    private var answerList: Array<Boolean> = arrayOf()
    private var givenAnswers = hashMapOf<Int, Int>()
    private var currentQuestionIndex = 0

    private val correctAnswerMapping = hashMapOf<Int, RadioButton>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)

        factory = MainViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setContentView(binding.root)
        setupCorrectAnswerMapping()
        setupQuestionList()
        setupNextListener()
        showQuestion(currentQuestionIndex)
    }

    private fun setupNextListener() {
        binding.btnNext.setOnClickListener {
            if (currentQuestionIndex == questionList.size - 1) {
                goToScoreScreen()
            } else {
                showQuestion(++currentQuestionIndex)
            }
        }
    }

    private fun setupCorrectAnswerMapping() {
        // Maps each RadioButton to a index
        arrayOf(
            binding.rdbAnswer1,
            binding.rdbAnswer2,
            binding.rdbAnswer3,
            binding.rdbAnswer4
        ).forEachIndexed { index, view ->
            correctAnswerMapping[index] = view
        }
    }

    private fun showQuestion(index: Int) {
        currentQuestionIndex = index
        val question = questionList[index]
        val currentQuestionText = getString(R.string.current_question, index + 1)
        binding.tvwQuestionTitle.text = question.question
        binding.actQuestions.setText(currentQuestionText, false)

        binding.btnNext.text = getString(
            if (currentQuestionIndex == questionList.size - 1) // Changes the text on the last one
                R.string.quiz_finish else R.string.next
        )

        showAnswers(index)
    }

    private fun showAnswers(index: Int) {
        val question = questionList[index]
        binding.rdbAnswer1.text = question.answer1
        binding.rdbAnswer2.text = question.answer2
        binding.rdbAnswer3.text = question.answer3
        binding.rdbAnswer4.text = question.answer4
        binding.rdgAnswers.setOnCheckedChangeListener(null)
        binding.rdgAnswers.check(givenAnswers[index] ?: -1)
        setupCorrectAnswerRadio(index)
    }

    private fun setupCorrectAnswerRadio(index: Int) {
        val question = questionList[index]
        val correctRadioButton = correctAnswerMapping[question.correctAnswer]
        binding.rdgAnswers.setOnCheckedChangeListener { _, checkedId ->
            val checked = findViewById<RadioButton>(checkedId)
            // Stores true if the RadioButton clicked was the right one or else otherwise
            answerList[index] = checkedId == correctRadioButton?.id
            givenAnswers[index] = checked.id // Stores the given answer to check it in case the
                                             // user returns to this question later.
        }
    }

    private fun setupQuestionList() {
        val questionBank = viewModel.getQuestionBankById(questionBankId)
        if (questionBank.first) {
            binding.tvwBankName.text = questionBank.second.name
            questionList = viewModel.getQuestionsByBankId(questionBank.second.id)
            questionList.shuffle()
            answerList = Array(questionList.size) { false } // All answers start as wrong (false)
            setupQuestionDropDown()
        }
    }

    private fun setupQuestionDropDown() {
        val questionIndexList = (1..questionList.size).map {
            getString(R.string.current_question, it)
        }
        val adapter = ArrayAdapter(this, R.layout.exposed_dropdown_item, questionIndexList)
        binding.actQuestions.setAdapter(adapter)
        binding.actQuestions.setOnItemClickListener { _, _, position, _ -> showQuestion(position) }
    }

    private fun goToScoreScreen() {
        Intent(this, ScoreActivity::class.java).apply {
            putExtra(EXTRA_ANSWERS_LIST, answerList.toBooleanArray())
            startActivity(this)
        }
        finish()
    }

}
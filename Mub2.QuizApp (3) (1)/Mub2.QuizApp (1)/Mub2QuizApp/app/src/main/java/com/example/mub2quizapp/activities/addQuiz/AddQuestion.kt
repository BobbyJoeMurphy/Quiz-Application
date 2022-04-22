package com.example.mub2quizapp.activities.addQuiz


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.IdRes
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mub2quizapp.R
import com.example.mub2quizapp.activities.extensions.hideKeyboard
import com.example.mub2quizapp.activities.model.QuestionBank
import com.example.mub2quizapp.activities.model.QuestionsModel
import com.example.mub2quizapp.activities.model.SQLiteHelper
import com.example.mub2quizapp.activities.viewModels.MainViewModel
import com.example.mub2quizapp.activities.viewModels.MainViewModelFactory
import com.example.mub2quizapp.databinding.FragmentAddQuestionBinding


class AddQuestion : Fragment() {

    private var edQuestion: EditText? = null
    private var actQuestionBanks: AutoCompleteTextView? = null
    private var edAnswer1: EditText? = null
    private var edAnswer2: EditText? = null
    private var edAnswer3: EditText? = null
    private var edAnswer4: EditText? = null
    private var btnAdd: Button? = null
    private var rdgCorrectAnswer: RadioGroup? = null

    private var questionBanks: ArrayList<QuestionBank> = arrayListOf()
    private var actSelectedPosition = -1

    private lateinit var addQuestionBinding: FragmentAddQuestionBinding
    private lateinit var factory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    private val validators = hashMapOf<Int, Boolean>()
    private val correctAnswerMapping = hashMapOf<Int, Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        factory = MainViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        addQuestionBinding = FragmentAddQuestionBinding.inflate(inflater, container, false)
        return addQuestionBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupListeners()
        setupValidators()
        setupCorrectAnswerMapping()
        setupDropDown()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupListeners() {
        btnAdd?.setOnClickListener { addQuestion() }

        actQuestionBanks?.setOnItemClickListener { _, _, position, _ ->
            actSelectedPosition = position
        }
    }

    private fun setupCorrectAnswerMapping() = arrayOf(
        view?.findViewById<RadioButton>(R.id.rdbCorrectAnswer1),
        view?.findViewById(R.id.rdbCorrectAnswer2),
        view?.findViewById(R.id.rdbCorrectAnswer3),
        view?.findViewById(R.id.rdbCorrectAnswer4),
    ).forEachIndexed { index, radioButton ->
        // Maps each RadioButton id to a index
        correctAnswerMapping[radioButton?.id ?: -1] = index
    }

    private fun setupValidators() {
        arrayOf(
            actQuestionBanks,
            edQuestion,
            edAnswer1,
            edAnswer2,
            edAnswer3,
            edAnswer4
        ).forEach { editText ->
            editText?.addTextChangedListener {
                // Sets each key of the validator to true if the EditText is not empty
                // Then enables the button based on the result of all validators
                validators[editText.id] = !it.isNullOrEmpty()
                enableButtonIfValid()
            }
        }

        rdgCorrectAnswer?.setOnCheckedChangeListener { group, _ ->
            validators[group.id] = true
            enableButtonIfValid()
        }
    }

    private fun enableButtonIfValid() {
        // Will enable the button if all values in validators are true
        btnAdd?.isEnabled = validators.map { entry ->
            entry.value
        }.all { valid -> valid }
    }

    private fun setupDropDown() {
        questionBanks = viewModel.getAllQuestionBanks()
        val banksNames = questionBanks.map { it.name }.toTypedArray() // Gets the names of the banks
        val adapter = ArrayAdapter(requireContext(), R.layout.exposed_dropdown_item, banksNames)
        actQuestionBanks?.setAdapter(adapter) // The adapter will control the items of the DropDown
    }

    private fun addQuestion() {
        val question = edQuestion?.text.toString()
        val answer1 = edAnswer1?.text.toString()
        val answer2 = edAnswer2?.text.toString()
        val answer3 = edAnswer3?.text.toString()
        val answer4 = edAnswer4?.text.toString()

        if (question.isEmpty() || answer1.isEmpty() || answer2.isEmpty() || answer3.isEmpty() || answer4.isEmpty()) {
            Toast.makeText(requireContext(), "please", Toast.LENGTH_SHORT).show()
        } else {
            val selectedQuestionBank = questionBanks[actSelectedPosition]
            val selectedCorrectAnswer = correctAnswerMapping[rdgCorrectAnswer?.checkedRadioButtonId]
            val std = QuestionsModel(
                bankId = selectedQuestionBank.id,
                correctAnswer = selectedCorrectAnswer ?: -1,
                question = question,
                answer1 = answer1,
                answer2 = answer2,
                answer3 = answer3,
                answer4 = answer4
            )
            val status = viewModel.insertQuestions(std)
            if (status) {
                Toast.makeText(requireContext(), "question added", Toast.LENGTH_SHORT).show()
                clearEditText()
                btnAdd?.hideKeyboard()
            } else {
                Toast.makeText(requireContext(), "record not saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView() {
        actQuestionBanks = findViewAndAddToValidation(R.id.actBanks) as AutoCompleteTextView?
        edQuestion = findViewAndAddToValidation(R.id.Question) as EditText?
        edAnswer1 = findViewAndAddToValidation(R.id.answer1) as EditText?
        edAnswer2 = findViewAndAddToValidation(R.id.answer2) as EditText?
        edAnswer3 = findViewAndAddToValidation(R.id.answer3) as EditText?
        edAnswer4 = findViewAndAddToValidation(R.id.answer4) as EditText?
        rdgCorrectAnswer = findViewAndAddToValidation(R.id.rdgCorrectAnswer) as RadioGroup?
        btnAdd = view?.findViewById(R.id.btn_Add)
    }

    private fun findViewAndAddToValidation(@IdRes id: Int): View? {
        // Adds a resource id to the validators and then returns the usual findViewById value
        validators[id] = false
        return view?.findViewById(id)
    }

    private fun clearEditText() {
        rdgCorrectAnswer?.clearCheck()
        validators[rdgCorrectAnswer?.id ?: -1] = false
        edQuestion?.setText("")
        edAnswer1?.setText("")
        edAnswer2?.setText("")
        edAnswer3?.setText("")
        edAnswer4?.setText("")
        edQuestion?.requestFocus()
    }

}







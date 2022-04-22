package com.example.mub2quizapp.activities.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mub2quizapp.R
import com.example.mub2quizapp.activities.data.QuestionsAdapter
import com.example.mub2quizapp.activities.model.QuestionBank
import com.example.mub2quizapp.activities.model.QuestionsModel
import com.example.mub2quizapp.activities.viewModels.MainViewModel
import com.example.mub2quizapp.activities.viewModels.MainViewModelFactory


class ListQuiz : Fragment() {

    private var questionsList: RecyclerView? = null
    private var actBanks: AutoCompleteTextView? = null

    private var questions: ArrayList<QuestionsModel>? = arrayListOf()
    private var questionBanks: ArrayList<QuestionBank> = arrayListOf()

    private lateinit var factory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        factory = MainViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actBanks = view.findViewById(R.id.actBanks)

        setupRecyclerView()
        setupDropDown()
        setupListeners()
    }

    private fun setupRecyclerView() {
        questionsList = view?.findViewById(R.id.recyclerView)
        questionsList?.adapter = QuestionsAdapter(questions ?: arrayListOf(), this::onDeleteClick)
        questionsList?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupListeners() {
        actBanks?.setOnItemClickListener { _, _, position, _ ->
            getList(position)
        }
    }

    private fun setupDropDown() {
        questionBanks = viewModel.getAllQuestionBanks()
        // Converts a list into another type
        val banksNames = questionBanks.map { it.name }.toTypedArray()
        val adapter = ArrayAdapter(requireContext(), R.layout.exposed_dropdown_item, banksNames)
        actBanks?.setAdapter(adapter)
    }

    private fun onDeleteClick(question: QuestionsModel) {
        if (viewModel.deleteQuestionById(question.id)) {
            val index = questions?.indexOf(question) ?: 0
            questions?.remove(question)
            // notifyItemRemoved() will produce an animation as item is removed
            questionsList?.adapter?.notifyItemRemoved(index)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getList(selectedPosition: Int) {
        questions?.clear()
        questions?.addAll(viewModel.getQuestionsByBankId(questionBanks[selectedPosition].id))
        questionsList?.adapter?.notifyDataSetChanged()
    }
}
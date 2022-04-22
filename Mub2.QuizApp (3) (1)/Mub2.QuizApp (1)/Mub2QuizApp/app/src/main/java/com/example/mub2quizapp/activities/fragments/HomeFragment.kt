package com.example.mub2quizapp.activities.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mub2quizapp.R
import com.example.mub2quizapp.activities.extensions.hideKeyboard
import com.example.mub2quizapp.activities.model.QuestionBank
import com.example.mub2quizapp.activities.viewModels.MainViewModel
import com.example.mub2quizapp.activities.viewModels.MainViewModelFactory
import com.example.mub2quizapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var factory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    private lateinit var binding: FragmentHomeBinding
    private var bankList: ArrayList<QuestionBank> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        factory = MainViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setup view operations only after the fragment has finished creating its View
        setupFab()
        setupAddBank()
        setupQuestionBankList()
    }

    private fun setupFab() {
        val floatingActionButton = binding.floatingActionButton3
        floatingActionButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.add_question)
        }
    }

    private fun setupQuestionBankList() {
        bankList = viewModel.getAllQuestionBanks()
        binding.listQuestionBanks.adapter = QuestionBankAdapter(bankList, false, { id ->
            // Handles the "Delete" button click
            if (viewModel.deleteQuestionBankById(id)) {
                // Only update the local list if the data has actually been updated
                val index = bankList.indexOfFirst { it.id == id } // Bank index of the given id
                bankList.removeAt(index)
                binding.listQuestionBanks.adapter?.notifyItemRemoved(index)
            }
        }, {})
    }

    private fun setupAddBank() {
        binding.edtQuestionBankName.addTextChangedListener {
            binding.btnAddBank.isEnabled = !it.isNullOrEmpty()
        }

        binding.btnAddBank.setOnClickListener {
            it.hideKeyboard()
            val bankName = binding.edtQuestionBankName.text
            val questionBank = QuestionBank(name = bankName.toString())
            if (viewModel.insertQuestionBank(questionBank)) {
                // Only update the local list if the data has actually been updated
                binding.edtQuestionBankName.setText("")
                bankList.add(0, questionBank)
                binding.listQuestionBanks.adapter?.notifyItemInserted(0)
            }
        }
    }

}
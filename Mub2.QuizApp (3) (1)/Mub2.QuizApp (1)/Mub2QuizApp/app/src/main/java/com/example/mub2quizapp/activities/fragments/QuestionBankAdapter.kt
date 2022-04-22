package com.example.mub2quizapp.activities.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mub2quizapp.R
import com.example.mub2quizapp.activities.model.QuestionBank

class QuestionBankAdapter(
    val questionBankList: ArrayList<QuestionBank>,
    val isReadOnly: Boolean,
    val onDeleteBankClick: (id: Int) -> Unit,
    val onCardClick: (id: Int) -> Unit
) : RecyclerView.Adapter<QuestionBankAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.question_bank_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount(): Int = questionBankList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvwBankName: TextView = itemView.findViewById(R.id.tvwQuestions)
        private val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
        private val cdvQuiz: CardView = itemView.findViewById(R.id.cdvQuiz)

        fun bind(position: Int) {
            val questionBank = questionBankList[position]
            tvwBankName.text = questionBank.name

            btnDelete.setOnClickListener { onDeleteBankClick(questionBank.id) }
            btnDelete.visibility = if (isReadOnly) View.GONE else View.VISIBLE

            cdvQuiz.setOnClickListener { onCardClick(questionBank.id) }
        }
    }
}
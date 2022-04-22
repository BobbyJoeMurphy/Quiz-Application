package com.example.mub2quizapp.activities.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mub2quizapp.R
import com.example.mub2quizapp.activities.extensions.setTextColorCompat
import com.example.mub2quizapp.activities.model.QuestionsModel

class QuestionsAdapter(
    private val list: ArrayList<QuestionsModel>,
    private var onClick: (question: QuestionsModel) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val correctAnswerMapping = hashMapOf<Int, Int>()

    init { // Called when the classes initializes (on the constructor)
        // Maps each TextView to a index
        arrayOf(
            R.id.answer1,
            R.id.answer2,
            R.id.answer3,
            R.id.answer4
        ).forEachIndexed { index, viewId ->
            correctAnswerMapping[index] = viewId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.question_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val question = holder.itemView.findViewById<TextView>(R.id.Questions)
        val answer1 = holder.itemView.findViewById<TextView>(R.id.answer1)
        val answer2 = holder.itemView.findViewById<TextView>(R.id.answer2)
        val answer3 = holder.itemView.findViewById<TextView>(R.id.answer3)
        val answer4 = holder.itemView.findViewById<TextView>(R.id.answer4)


        val deleteBtn = holder.itemView.findViewById<TextView>(R.id.Delete_button)
        val questionModel = list[position]


        fun resetCorrectAnswer() {
            correctAnswerMapping.values.forEach {
                val textView = holder.itemView.findViewById<TextView>(it)
                textView.setTextColorCompat(R.color.black)
            }
        }

        fun setCorrectAnswer(textView: TextView?) {
            textView?.setTextColorCompat(R.color.correct_answer)
        }

        question.text = questionModel.question
        answer1.text = questionModel.answer1
        answer2.text = questionModel.answer2
        answer3.text = questionModel.answer3
        answer4.text = questionModel.answer4

        deleteBtn.setOnClickListener { onClick.invoke(questionModel) }
        correctAnswerMapping.keys
        // Finds the TextView in the mapping based on the question's correct answer
        val textView = holder.itemView.findViewById<TextView>(
            correctAnswerMapping[questionModel.correctAnswer] ?: -1
        )
        resetCorrectAnswer()
        setCorrectAnswer(textView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
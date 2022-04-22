package com.example.mub2quizapp.activities

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mub2quizapp.R
import com.example.mub2quizapp.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBinding
    private val answerList by lazy { intent.getBooleanArrayExtra(EXTRA_ANSWERS_LIST) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showScore()
        setupListener()
    }

    private fun setupListener() {
        binding.btnOk.setOnClickListener { finish() }
    }

    private fun showScore() {
        val correctCount = answerList?.filter { it }?.size // Filters correct (true) answers
        binding.tvwScoreTitle.text = getString(R.string.score_title, correctCount, answerList?.size)
        if (correctCount != null && correctCount > 0) animateScoreProgress(correctCount)
    }

    private fun animateScoreProgress(correctAnswerCount: Int) {
        // Multiplies the steps by 100 to give the animation a motion effect
        val values = IntArray(correctAnswerCount * 100) { it + 1 }
        binding.pgbScore.max = answerList?.size?.times(100) ?: 0
        with(ValueAnimator.ofInt(*values)) {
            addUpdateListener {
                binding.pgbScore.progress = it.animatedValue as Int
            }
            start()
        }
    }

}
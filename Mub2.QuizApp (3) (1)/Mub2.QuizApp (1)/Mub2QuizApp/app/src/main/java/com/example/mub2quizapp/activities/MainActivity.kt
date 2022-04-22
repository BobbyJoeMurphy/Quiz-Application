package com.example.mub2quizapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mub2quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.TeacherButton.setOnClickListener {
            val intent = Intent(this, TeacherActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.StudentButton.setOnClickListener {
            val intent = Intent(this, StudentActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ResultActivity : AppCompatActivity() {

    var tvName: TextView? = null
    var tvScore: TextView? = null
    var finishButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvName = findViewById(R.id.tv_name)
        tvScore = findViewById(R.id.tv_score)

        var username = intent.getStringExtra(Constants.USER_NAME)
        tvName?.text = username

        var score: Int = intent.getIntExtra(Constants.SCORE, 0)
        var maxScore: Int = intent.getIntExtra(Constants.MAX_SCORE, 0)
        tvScore?.text = "Your Score is ${score} out of ${maxScore}"

        finishButton = findViewById(R.id.btn_finish)

        finishButton?.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java);
            startActivity(intent)
            finish()
        }
    }
}
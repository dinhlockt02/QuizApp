package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = this.findViewById<Button>(R.id.startButton);

        startButton.setOnClickListener {
            val nameInput = findViewById<AppCompatEditText>(R.id.nameInput)

            val name = nameInput.text.toString()

            if(name.isEmpty())
            {
                Toast.makeText(this, "Name must not be empty", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra(Constants.USER_NAME, name)
                startActivity(intent);
                finish()
            }
        }
    }
}
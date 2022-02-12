package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.Nullable
import org.w3c.dom.Text

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    var tvQuestion : TextView? = null
    var image : ImageView? = null
    var progressBar: ProgressBar? = null
    var progressText: TextView? = null
    var answerOne: TextView? = null
    var answerTwo: TextView? = null
    var answerThree: TextView? = null
    var answerFour: TextView? = null

    var selectedOption: Int = -1

    var submitButton: Button? = null

    var isSubmit: Boolean = false


    val questions : ArrayList<Question> = Constants.getQuestions()
    var currentProgress: Int = 0
    var max: Int = questions.size
    var score: Int = 0

    var username: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        username = intent.getStringExtra(Constants.USER_NAME)

        tvQuestion = findViewById(R.id.question)
        image = findViewById(R.id.image)
        progressBar = findViewById(R.id.progressBar)
        progressText = findViewById(R.id.progressText)
        answerOne = findViewById(R.id.tv_option_one)
        answerTwo = findViewById(R.id.tv_option_two)
        answerThree = findViewById(R.id.tv_option_three)
        answerFour = findViewById(R.id.tv_option_four)
        submitButton = findViewById(R.id.btnSubmit)

        answerOne?.setOnClickListener(this)
        answerTwo?.setOnClickListener(this)
        answerThree?.setOnClickListener(this)
        answerFour?.setOnClickListener(this)
        submitButton?.setOnClickListener(this)


        setQuestion(questions[currentProgress], currentProgress, max)
    }

    fun setQuestion(question: Question, progress: Int, max: Int) {
        tvQuestion?.text = question.question
        image?.setImageResource(question.image)
        progressBar?.progress = progress
        progressBar?.max = max
        progressText?.text = "$progress/$max"
        answerOne?.text = question.optionOne
        answerTwo?.text = question.optionTwo
        answerThree?.text = question.optionThree
        answerFour?.text = question.optionFour
        setDefaultSelectOption()

    }

    fun setDefaultSelectOption() {
        val answerOptions = ArrayList<TextView?>();
        answerOptions.add(answerOne)
        answerOptions.add(answerTwo)
        answerOptions.add(answerThree)
        answerOptions.add(answerFour)
        for (option in answerOptions) {
            option?.background = getDrawable(R.drawable.default_option_border_bg)
        }
    }

    fun setSelectedOption(answerOption: TextView?, selectedOption: Int) {
        if(isSubmit)
            return
        setDefaultSelectOption()
        answerOption?.background = getDrawable(R.drawable.selected_option_border_bg)
        this.selectedOption = selectedOption
    }


    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.tv_option_one -> {
                setSelectedOption(answerOne, 0)
            }
            R.id.tv_option_two -> {
                setSelectedOption(answerTwo, 1)
            }
            R.id.tv_option_three -> {
                setSelectedOption(answerThree, 2)
            }
            R.id.tv_option_four -> {
                setSelectedOption(answerFour, 3)
            }

            R.id.btnSubmit -> {
                if (!isSubmit)
                    submitButtonClickHandler()
                else if (!(currentProgress == max - 1))
                {
                    currentProgress++;
                    setQuestion(questions[currentProgress], currentProgress, max)
                    isSubmit =false
                    selectedOption = -1
                } else {
                    isSubmit =false
                    selectedOption = -1
                    var intent = Intent(this, ResultActivity::class.java);
                    intent.putExtra(Constants.USER_NAME, username)
                    intent.putExtra(Constants.SCORE, score)
                    intent.putExtra(Constants.MAX_SCORE, max)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun submitButtonClickHandler() {
        if(selectedOption < 0 || selectedOption > 3)
            return
        var correctRc =  R.drawable.correct_answer_border_bg
        var wrongRc = R.drawable.wrong_answer_border_bg
        if (isCorrectAnswer())
        {
            score++
            when(selectedOption) {
                0 -> {
                    answerOne?.background = getDrawable(correctRc)
                }
                1 -> {
                    answerTwo?.background = getDrawable(correctRc)
                }
                2 -> {
                    answerThree?.background = getDrawable(correctRc)
                }
                3 -> {
                    answerFour?.background = getDrawable(correctRc)
                }
            }
        } else
        {

            when(selectedOption) {
                0 -> {
                    answerOne?.background = getDrawable(wrongRc)
                }
                1 -> {
                    answerTwo?.background = getDrawable(wrongRc)
                }
                2 -> {
                    answerThree?.background = getDrawable(wrongRc)
                }
                3 -> {
                    answerFour?.background = getDrawable(wrongRc)
                }
            }
            when(questions[currentProgress].correctAnswer - 1) {
                0 -> {
                    answerOne?.background = getDrawable(correctRc)
                }
                1 -> {
                    answerTwo?.background = getDrawable(correctRc)
                }
                2 -> {
                    answerThree?.background = getDrawable(correctRc)
                }
                3 -> {
                    answerFour?.background = getDrawable(correctRc)
                }
            }
        }
        isSubmit = true
        if(currentProgress === max - 1) {
            submitButton?.text = "FINISH"
        } else {
            submitButton?.text = "NEXT QUESTION"
        }
    }

    private fun isCorrectAnswer(): Boolean {
        return questions[currentProgress].correctAnswer == (selectedOption + 1)
    }

}
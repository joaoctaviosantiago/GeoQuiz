package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import java.text.DecimalFormat


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_brazil, false),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_america, true),
        Question(R.string.question_asia, true),
    )
    private var currentIndex = 0
    private var grade = 0.0
    private var answered = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
            isAnswered()
        }
        binding.falseButton.setOnClickListener {
            checkAnswer(false)
            isAnswered()
        }

        binding.previousButton.setOnClickListener {
            currentIndex = if (currentIndex == 0) {
                questionBank.size - 1
            } else {
                (currentIndex - 1) % questionBank.size
            }
            updateQuestion()
            isAnswered()

            if (answered.size == questionBank.size) {
                score()
            }
        }

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            isAnswered()

            if (answered.size == questionBank.size) {
                score()
            }
        }

        updateQuestion()

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }

    private fun updateQuestion() {
//      getting question ID from resources>string
        val questionTextResId = questionBank[currentIndex].textResId

//      binding it to the textview
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId: Int

//      accessing the message's id from resources>string
//      passing it to the toast afterwards
        if (userAnswer == correctAnswer) {
            messageResId = R.string.correct_toast
            grade += 1
        } else {
            messageResId = R.string.incorrect_toast
        }

//      adding answered question to the answered mutable list
        answered.add(currentIndex)

//      making toast with the text from the string folder
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun isAnswered() {

//      deactivating or activating buttons if the question has been answered
        if (currentIndex in answered) {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        } else {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }
    }

    private fun score() {

//      calculating score and formatting it
        val finalScore = grade * (100.0/6)
        val formatting = DecimalFormat("#.##")

//      displaying the toast with the message after
        Toast.makeText(
            this,
            "You've answered ${formatting.format(finalScore)}% of the questions correctly!",
            Toast.LENGTH_LONG
        ).show()
    }
}
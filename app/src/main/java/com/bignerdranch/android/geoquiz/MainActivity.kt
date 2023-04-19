package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import java.text.DecimalFormat


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate(Bundle?) called")
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }
        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }

        binding.previousButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            updateQuestion()
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
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
        val questionTextResId = quizViewModel.currentQuestionText

//      binding it to the textview
        binding.questionTextView.setText(questionTextResId)

        completed()

        var answeredQuestions = 0

        for (question in quizViewModel.questionBank) {
            if (question.complete) {
                answeredQuestions += 1
            }
            if (answeredQuestions == quizViewModel.questionBank.size) {
                //      calculating score and formatting it
                val finalScore = quizViewModel.currentScore * (100.0/6)
                val formatting = DecimalFormat("#.##")

                //      displaying the toast with the message after
                Toast.makeText(
                    this,
                    "You've answered ${formatting.format(finalScore)}% of the questions correctly!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {

        if (!quizViewModel.questionBank[quizViewModel.currentIndex].complete) {
            val correctAnswer = quizViewModel.currentQuestionAnswer
            val messageResId: Int
            quizViewModel.questionBank[quizViewModel.currentIndex].complete = true

            if (userAnswer == correctAnswer) {
                messageResId = R.string.correct_toast
                quizViewModel.currentScore += 1
            } else {
                messageResId = R.string.incorrect_toast
            }

            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        }

        completed()
    }

    private fun completed() {
        if (quizViewModel.questionBank[quizViewModel.currentIndex].complete) {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        } else {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }
    }
}
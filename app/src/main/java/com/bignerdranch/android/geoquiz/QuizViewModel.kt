package com.bignerdranch.android.geoquiz


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val CURRENT_SCORE_KEY = "CURRENT_SCORE_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    val questionBank = listOf(
        Question(R.string.question_brazil, answer=false, complete=false),
        Question(R.string.question_oceans, answer=true, complete=false),
        Question(R.string.question_mideast, answer=false, complete=false),
        Question(R.string.question_africa, answer=false, complete=false),
        Question(R.string.question_america, answer=true, complete=false),
        Question(R.string.question_asia, answer=true, complete=false),
    )

    var currentIndex: Int
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    var currentScore: Double
        get() = savedStateHandle[CURRENT_SCORE_KEY]  ?: 0.0
        set(value) = savedStateHandle.set(CURRENT_SCORE_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious() {
        currentIndex = if (currentIndex == 0) {
            questionBank.size - 1
        } else {
            (currentIndex - 1) % questionBank.size
        }
    }

}
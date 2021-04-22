package com.upitnik.playwithlessons.ui.questions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.questions.AnswerData
import com.upitnik.playwithlessons.data.model.questions.QuestionData
import com.upitnik.playwithlessons.data.model.questions.QuestionProvider
import com.upitnik.playwithlessons.databinding.ActivityQuestionsBinding

class ActivityQuestions : AppCompatActivity(), OnQuestionActionListener {

    companion object {
        fun create(context: Context): Intent {
            return Intent(context, ActivityQuestions::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }
    }

    private lateinit var binding: ActivityQuestionsBinding
    private val questions = QuestionProvider.getQuestions()
    private var count: Int = 0
    private var questionPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initToolbar()
        showNewQuestion(questions.first())
        binding.tvPoints.text = "$count puntos"
        updateSteps()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Problemas matematicos"
    }

    private fun updateSteps() {
        binding.tvSteps.text = " ${questionPosition + 1} de ${questions.size}"
    }

    private fun setPoints() {
        count += 1
        binding.tvPoints.text = "$count puntos"
    }

    private fun nextQuestion() {
        questionPosition += 1
        if (questions.size <= questionPosition) {
            goToResult()
        } else {
            showNewQuestion(questions[questionPosition])
        }
    }

    private fun goToResult() {
        startActivity(ResultActivity.create(this, count, questions.size))
    }

    private fun showNewQuestion(questionData: QuestionData) {
        updateSteps()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        fragmentTransaction.replace(R.id.container, QuestionsFragment.newInstance(questionData))
        fragmentTransaction.commit()
    }

    override fun onAnswerClickedQuestions(answer: AnswerData) {
        Handler(Looper.myLooper()!!).postDelayed(
            {
                updateView(answer)
            },
            400 // value in milliseconds
        )
    }

    private fun updateView(answer: AnswerData) {
        if (answer.isCorrect) {
            setPoints()
        }
        nextQuestion()
    }
}
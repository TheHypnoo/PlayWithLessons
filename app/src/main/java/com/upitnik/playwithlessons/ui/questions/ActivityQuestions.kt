package com.upitnik.playwithlessons.ui.questions

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.upitnik.playwithlessons.data.model.questions.QuestionProvider
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.questions.AnswerData
import com.upitnik.playwithlessons.data.model.questions.QuestionData
import com.upitnik.playwithlessons.data.model.relate.RelateData
import com.upitnik.playwithlessons.databinding.ActivityPrincipalBinding
import com.upitnik.playwithlessons.ui.relate.OnRelateButtonActionListener

class ActivityQuestions : AppCompatActivity(), OnRelateButtonActionListener {

    companion object {
        fun create(context: Context): Intent {
            return Intent(context, ActivityQuestions::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }
    }
    private lateinit var binding: ActivityPrincipalBinding
    private val questions = QuestionProvider.getQuestions()
    private var count:Int = 0
    private var questionPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
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
        supportActionBar?.title = "Preguntas y Respuestas"
    }
    private fun updateSteps() {
        binding.tvSteps.text = " ${questionPosition+1} de ${questions.size}"
    }
    private fun setPoints() {
        count += 1
        binding.tvPoints.text = "$count puntos"
    }
    private fun nextQuestion() {
        questionPosition += 1
        if(questions.size <= questionPosition){
            goToResult()
        }else{
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

    override fun onAnswerClickedRelate(answer: RelateData) {
        Handler(Looper.myLooper()!!).postDelayed(
            {
                updateView(answer)
            },
            400 // value in milliseconds
        )
    }

    private fun updateView(answer: RelateData) {
        /*if(answer.isCorrect){
            setPoints()
        }*/
        nextQuestion()
    }
}
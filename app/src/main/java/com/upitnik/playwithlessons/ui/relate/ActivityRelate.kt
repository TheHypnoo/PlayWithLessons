package com.upitnik.playwithlessons.ui.relate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.questions.AnswerData
import com.upitnik.playwithlessons.data.model.relate.RelateData
import com.upitnik.playwithlessons.data.model.relate.RelateProvider
import com.upitnik.playwithlessons.databinding.ActivityRelateBinding
import com.upitnik.playwithlessons.ui.questions.ResultActivity

class ActivityRelate : AppCompatActivity() {
    companion object {
        fun create(context: Context): Intent {
            return Intent(context, ActivityRelate::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }
    }

    private lateinit var binding: ActivityRelateBinding
    private val relatequestions = RelateProvider.getQuestions()
    private var count: Int = 0
    private var relatePosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRelateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initToolbar()
        showNewQuestion(relatequestions.first())
        binding.tvPoints.text = "$count puntos"
        updateSteps()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Relacionar"
    }

    private fun updateSteps() {
        binding.tvSteps.text = " ${relatePosition + 1} de ${relatequestions.size}"
    }

    private fun setPoints() {
        count += 1
        binding.tvPoints.text = "$count puntos"
    }

    private fun nextQuestion() {
        relatePosition += 1
        if (relatequestions.size <= relatePosition) {
            goToResult()
        } else {
            showNewQuestion(relatequestions[relatePosition])
        }
    }

    private fun goToResult() {
        startActivity(ResultActivity.create(this, count, relatequestions.size))
    }

    private fun showNewQuestion(relateData: RelateData) {
        updateSteps()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        fragmentTransaction.replace(R.id.container, RelateFragment.newInstance(relateData))
        fragmentTransaction.commit()
    }

    /* override fun onAnswerClicked(answer: AnswerData) {
         Handler(Looper.myLooper()!!).postDelayed(
             {
                 updateView(answer)
             },
             400 // value in milliseconds
         )
     }*/
    /*private fun updateView(answer: AnswerData) {
        if (answer.isCorrect) {
            setPoints()
        }
        nextQuestion()
    }*/
}
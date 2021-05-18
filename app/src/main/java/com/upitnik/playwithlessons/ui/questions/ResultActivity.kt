package com.upitnik.playwithlessons.ui.questions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.upitnik.playwithlessons.data.model.subject.Subject
import com.upitnik.playwithlessons.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    companion object {
        private const val RESULT_DATA = "result_data"
        private const val SIZE_DATA = "size_data"
        private const val SUBJECT_DATA = "subject_data"
        private const val LEVEL_NUMBER = "level_number"

        fun create(context: Context, result: Int, size: Int, subject: Subject, level: Int): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                putExtra(RESULT_DATA, result)
                putExtra(SIZE_DATA, size)
                putExtra(SUBJECT_DATA, subject)
                putExtra(LEVEL_NUMBER, level)
            }
        }
    }

    private val count by lazy { intent.getIntExtra(RESULT_DATA, 0) }
    private val size by lazy { intent.getIntExtra(SIZE_DATA, 0) }
    private val subject by lazy {
        intent.getSerializableExtra(
            SUBJECT_DATA
        )
    }
    private val level by lazy { intent.getIntExtra(LEVEL_NUMBER, 0) }
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initListeners()
        showResult()
    }

    private fun showResult() {
        binding.tvResult.text = getTextFromResult(count)
        animateProgressResult(count)
    }

    private fun initListeners() {
        binding.btnBack.setOnClickListener { resetTest() }
    }

    private fun animateProgressResult(count: Int) {
        val result = (count * 100) / size
        //Pon aquí tu animacion
        // val anim = CircularAnimation( progressBar = binding.pbLvl, to = result)
        //anim.duration = 1000
        //binding.pbLvl.startAnimation(anim)
    }

    private fun getTextFromResult(count: Int): String {
        return when (count) {
            in 0..3 -> "JUNIOR"
            in 4..5 -> "MID"
            in 6..Int.MAX_VALUE -> "SENIOR DE MANUAL"
            else -> ""
        }
    }

    private fun resetTest() {
        startActivity(ActivityQuestions.create(this))
    }

    override fun onBackPressed() {
        resetTest()
    }
}
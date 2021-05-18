package com.upitnik.playwithlessons.ui.questions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.data.model.questions.Answer
import com.upitnik.playwithlessons.data.model.questions.Question
import com.upitnik.playwithlessons.data.model.subject.Subject
import com.upitnik.playwithlessons.databinding.ActivityQuestionsBinding
import com.upitnik.playwithlessons.repository.WebService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await

class ActivityQuestions : AppCompatActivity(), OnQuestionActionListener {

    companion object {
        fun create(context: Context): Intent {
            return Intent(context, ActivityQuestions::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }
    }

    private lateinit var binding: ActivityQuestionsBinding
    private var count: Int = 0
    private var questionPosition = 0
    private var ListQuestions: List<Question> = listOf()
    private var subject: Subject = Subject(0, 0, "", "", 0, 0, 0)
    private var level: Levels = Levels(0, 0, 0, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.IO).launch {
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                getQuestions()
            }
        }
    }

    private suspend fun getQuestions(): List<Question> {
        val listQuestions: ArrayList<Question> = arrayListOf()
        var questions: List<Question> = listOf()
        level = intent.getSerializableExtra("Level") as Levels
        subject = intent.getSerializableExtra("Subject") as Subject
        val call =
            WebService.RetrofitClient.webService.getQuestions(level.number, subject.id).await()
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            listQuestions.clear()
            call.forEach { question ->
                listQuestions.add(
                    Question(
                        question.answer,
                        question.id,
                        question.image,
                        question.level,
                        question.stagecorrect,
                        question.statement,
                        question.subject
                    )
                )
                questions = listQuestions.toList()
                ListQuestions = questions
            }
        }
        CoroutineScope(Dispatchers.Main).launch { initUI() }
        return questions
    }

    private fun initUI() {
        initToolbar()
        showNewQuestion(ListQuestions.first())
        binding.tvPoints.text = "$count puntos"
        updateSteps()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "${subject?.name}"
    }

    private fun updateSteps() {
        binding.tvSteps.text = " ${questionPosition + 1} de ${ListQuestions.size}"
    }

    private fun setPoints() {
        count += 1
        binding.tvPoints.text = "$count puntos"
    }

    private fun nextQuestion() {
        questionPosition += 1
        if (ListQuestions.size <= questionPosition) {
            goToResult()
        } else {
            showNewQuestion(ListQuestions[questionPosition])
        }
    }

    private fun goToResult() {
        startActivity(ResultActivity.create(this, count, ListQuestions.size, subject, level.id))
    }

    private fun showNewQuestion(questionData: Question) {
        updateSteps()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        fragmentTransaction.replace(R.id.container, QuestionsFragment.newInstance(questionData))
        fragmentTransaction.commit()
    }

    override fun onAnswerClickedQuestions(answer: Answer) {
        Handler(Looper.myLooper()!!).postDelayed(
            {
                updateView(answer)
            },
            400 // value in milliseconds
        )
    }

    private fun updateView(answer: Answer) {
        if (answer.correct == 1) {
            setPoints()
        }
        nextQuestion()
    }

}
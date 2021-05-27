package com.upitnik.playwithlessons.ui.questions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.core.extensions.gone
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
    private var listQuestions: List<Question> = listOf()
    private var subject: Subject = Subject(0, 0, "", "", 0, 0, 0)
    private var level: Int = 0

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
        level = intent.getIntExtra("NumberLevel", 0)
        subject = intent.getSerializableExtra("Subject") as Subject
        val call =
            WebService.RetrofitClient.webService.getQuestions(level, subject.id, FirebaseAuth.getInstance().currentUser!!.uid).await()
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            listQuestions.clear()
            call.forEach { question ->
                if (question.stagecorrect == 0) {
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
                }
                questions = listQuestions.toList()
                this@ActivityQuestions.listQuestions = questions
            }
        }
        CoroutineScope(Dispatchers.Main).launch { initUI() }
        return questions
    }

    private fun initUI() {
        initToolbar()
        if (listQuestions.isNotEmpty()) {
            showNewQuestion(listQuestions.first())
        }
        binding.tvPoints.text = "$count puntos"
        updateSteps()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = subject.name
    }

    private fun updateSteps() {
        binding.tvSteps.text = " ${questionPosition + 1} de ${listQuestions.size}"
    }

    private fun setPoints() {
        count += 1
        binding.tvPoints.text = "$count puntos"
    }

    private fun nextQuestion() {
        questionPosition += 1
        if (listQuestions.size <= questionPosition) {
            binding.tvSteps.gone()
            /*var i = 0
            listQuestions.forEach{ question ->
                if(question.stagecorrect == 1) {
                    i++
                }
            }
            if(i == listQuestions.size) {

            }*/
            if (count == listQuestions.size) {
                println("Hago put diciendo que esta finalizado el level!")
            }
            goToResult(count, listQuestions.size, level, subject)
        } else {
            showNewQuestion(listQuestions[questionPosition])
        }
    }

    private fun goToResult(count: Int, sizeList: Int, level: Int, Subject: Subject) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        fragmentTransaction.replace(
            R.id.container,
            ResultQuestionFragment.newInstance(count, sizeList, level, Subject)
        )
        fragmentTransaction.commit()
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
            700 // value in milliseconds
        )
    }

    private fun updateView(answer: Answer) {
        if (answer.correct == 1) {
            setPoints()
        }
        nextQuestion()
    }

    private suspend fun updateQuestion() {
        //WebService.RetrofitClient.webService.putQuestion(question.id, question).await()
    }

}
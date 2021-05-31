package com.upitnik.playwithlessons.ui.questions

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.core.extensions.isNull
import com.upitnik.playwithlessons.core.extensions.load
import com.upitnik.playwithlessons.core.extensions.shakeRotate
import com.upitnik.playwithlessons.core.extensions.vibrate
import com.upitnik.playwithlessons.data.model.authentication.User
import com.upitnik.playwithlessons.data.model.questions.Answer
import com.upitnik.playwithlessons.data.model.questions.Question
import com.upitnik.playwithlessons.data.model.userQuestions
import com.upitnik.playwithlessons.databinding.FragmentQuestionsBinding
import com.upitnik.playwithlessons.repository.WebService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await

class QuestionsFragment : Fragment(R.layout.fragment_questions) {

    private var _binding: FragmentQuestionsBinding? = null
    private val binding get() = _binding!!

    private var listener: OnQuestionActionListener? = null

    lateinit var adapter: AnswerAdapter

    lateinit var question: Question
    private var correction = false
    private var listOneUser = listOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            question = it.getSerializable(INTENT_QUESTION) as Question
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuestionsBinding.bind(view)
        CoroutineScope(Dispatchers.IO).launch {
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                getUser()
            }
        }
        initQuestion(question)
    }

    private suspend fun getUser() {
        listOneUser = WebService.RetrofitClient.webService.getUser(
            FirebaseAuth.getInstance().currentUser!!.uid
        ).await()
    }

    private fun initTitleQuestion(title: String) {
        binding.tvQuestion.text = title
    }

    private fun initImageQuestion(header: String) {
        if (header.isNotEmpty()) {
            binding.ivHeader.load(header)
        }
    }

    private fun checkIsCorrect(answer: Answer, button: Button) {
        if (!correction) {
            correction = true
            if (answer.correct == 1) {
                question.stagecorrect = 1
                button.isClickable = false
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                        updateUserQuestion()
                        updateUser()
                    }
                }
            } else {
                context?.vibrate(1000)
                binding.rvAnswer.shakeRotate()
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
        }
    }

    private suspend fun updateUser() {
        listOneUser[0].score += 10
        listOneUser[0].experience += 15
        WebService.RetrofitClient.webService.putUser(
            FirebaseAuth.getInstance().currentUser!!.uid,
            listOneUser[0]
        ).await()
    }

    private suspend fun updateUserQuestion() {
        WebService.RetrofitClient.webService.putQuestion(
            question.id,
            FirebaseAuth.getInstance().currentUser!!.uid,
            userQuestions(1)
        ).await()
    }

    private fun initQuestion(questions: Question) {
        if (!questions.image.isNull()) {
            initImageQuestion(questions.image!!)
        }
        initTitleQuestion(questions.statement)
        formatQuestion(questions.statement)
        initAnswerQuestion(questions.answer)
    }

    private fun initAnswerQuestion(answers: List<Answer>) {
        binding.rvAnswer.layoutManager = LinearLayoutManager(requireContext())
        adapter = AnswerAdapter(answers) { answerData, position ->
            onAnswerSelected(
                answerData,
                position
            )
        }
        binding.rvAnswer.adapter = adapter
    }

    private fun onAnswerSelected(result: Answer, position: Int) {
        val viewHolder = binding.rvAnswer.findViewHolderForAdapterPosition(position)
        checkIsCorrect(result, (viewHolder as AnswerViewHolder).btnAnswer)
        listener?.onAnswerClickedQuestions(result)
        for (d in question.answer.indices) {
            binding.rvAnswer.findViewHolderForAdapterPosition(d)!!.itemView.isClickable = false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnQuestionActionListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun formatQuestion(question: String) {
//        Usamos la palabra reservada <code>var<code> para...
        val parts = question.split("<code>")
        when (parts.size) {
            1 -> {
    //            return SpannableString(parts[0])
            }
            3 -> {
                val spannable =
                    SpannableString("${parts[0].trimEnd()} ${parts[1].trim() + parts[2]}")

                spannable.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(activity as Context, R.color.darkGreen)),
                    parts[0].length, parts[0].length + parts[1].length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                /*spannable.setSpan(
                    BackgroundColorSpan(
                        ContextCompat.getColor(
                            activity as Context,
                            R.color.primaryDarkColor
                        )
                    ),
                    parts[0].length + 1, parts[0].length + parts[1].length + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )*/

                /* spannable.setSpan(
                     StyleSpan(Typeface.NORMAL),
                     parts[0].length + 1, parts[0].length + parts[1].length + 1,
                     Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                 )*/


                binding.tvQuestion.text = spannable
            }
            5 -> {
                val spannable =
                    SpannableString("${parts[0].trimEnd()} ${parts[1].trim() + parts[2]} ${parts[3] + parts[4].trimEnd()}")

                spannable.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(activity as Context, R.color.darkGreen)),
                    parts[0].length, parts[0].length + parts[1].length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                spannable.setSpan(
                    BackgroundColorSpan(ContextCompat.getColor(activity as Context, R.color.darkGreen)),
                    parts[2].length, parts[3].length + parts[4].length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                /*spannable.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(activity as Context, R.color.darkGreen)),
                    parts[3].length, parts[3].length + parts[4].length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )*/
                binding.tvQuestion.text = spannable
            }
        }
    }

    companion object {

        const val INTENT_QUESTION = "intent_question"

        @JvmStatic
        fun newInstance(question: Question) =
            QuestionsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(INTENT_QUESTION, question)
                }
            }
    }
}

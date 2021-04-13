package com.upitnik.playwithlessons.ui.questions

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.upitnik.playwithlessons.data.model.questions.AnswerData
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.questions.QuestionData
import com.upitnik.playwithlessons.databinding.FragmentQuestionsBinding
import com.upitnik.playwithlessons.questions.OnQuestionActionListener

class QuestionsFragment : Fragment() {

    private var _binding: FragmentQuestionsBinding? = null
    private val binding get() = _binding!!

    private var listener: OnQuestionActionListener? = null

    lateinit var adapter:AnswerAdapter

    lateinit var question: QuestionData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            question = it.getSerializable(INTENT_QUESTION) as QuestionData
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initQuestion(question)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

//    private fun disableAnswer(){
//
//        fo
//        adapter.itemCount
//    }

    private fun initTitleQuestion(title: String) {
        binding.tvQuestion.text = title
    }

    private fun initImageQuestion(header: String) {
        if (header.isNotEmpty()) {
            Glide.with(this).load(header).into(binding.ivHeader)
        }
    }

    private fun checkIsCorrect(answer: AnswerData, button: Button){
        if(answer.isCorrect){
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
        }else{
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
        }
//        disableAnswer()
    }


    private fun initQuestion(questions: QuestionData) {
        initImageQuestion(questions.header)
//        initTitleQuestion(questions.title)
        formatQuestion(questions.title)
        initAnswerQuestion(questions.answers)
    }

    private fun initAnswerQuestion(answers: List<AnswerData>) {
        binding.rvAnswer.layoutManager = LinearLayoutManager(requireContext())
        adapter = AnswerAdapter(answers) { answerData, position ->  onAnswerSelected(answerData, position)}
        binding.rvAnswer.adapter = adapter
    }

    private fun onAnswerSelected(result: AnswerData, position: Int) {
        val viewHolder = binding.rvAnswer.findViewHolderForAdapterPosition(position)
        checkIsCorrect(result, (viewHolder  as AnswerViewHolder).btnAnswer)

        listener?.onAnswerClicked(result)
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

    fun formatQuestion(question:String){
//        Usamos la palabra reservada <code>var<code> para...
        val parts = question.split("<code>")
        if(parts.size == 1){
//            return SpannableString(parts[0])
        }else if(parts.size == 3){
            val spannable = SpannableString("${parts[0]} ${parts[1]} ${parts[2]}")

            spannable.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(activity as Context, R.color.orange)),
                parts[0].length +1,  parts[0].length + parts[1].length +1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            spannable.setSpan(
                BackgroundColorSpan(ContextCompat.getColor(activity as Context, R.color.primaryDarkColor)),
                parts[0].length +1,  parts[0].length + parts[1].length +1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            spannable.setSpan(
                StyleSpan(Typeface.NORMAL),
                parts[0].length +1,  parts[0].length + parts[1].length +1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)


            binding.tvQuestion.text = spannable
        }
    }


    companion object {

        const val INTENT_QUESTION = "intent_question"

        @JvmStatic
        fun newInstance(question: QuestionData) =
            QuestionsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(INTENT_QUESTION, question)
                }
            }
    }
}

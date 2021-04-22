package com.upitnik.playwithlessons.ui.relate

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.relate.RelateAnswerLeft
import com.upitnik.playwithlessons.data.model.relate.RelateAnswerRight
import com.upitnik.playwithlessons.data.model.relate.RelateAnswers
import com.upitnik.playwithlessons.data.model.relate.RelateData
import com.upitnik.playwithlessons.databinding.FragmentRelateBinding
import com.upitnik.playwithlessons.ui.questions.AnswerAdapter
import com.upitnik.playwithlessons.ui.questions.OnQuestionActionListener
import com.upitnik.playwithlessons.ui.relate.adapters.RelateAdapter
import com.upitnik.playwithlessons.ui.relate.adapters.concat.RelateLeftConcatAdapter
import com.upitnik.playwithlessons.ui.relate.adapters.concat.RelateRightConcatAdapter

//
class RelateFragment : Fragment(R.layout.fragment_relate) {

    private var _binding: FragmentRelateBinding? = null
    private val binding get() = _binding!!

    private var listener: OnRelateButtonActionListener? = null

    lateinit var adapter: AnswerAdapter

    lateinit var question: RelateData

    private lateinit var concatAdapter: ConcatAdapter

    companion object {

        const val INTENT_RELATE = "intent_relate"

        @JvmStatic
        fun newInstance(question: RelateData) =
            RelateFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(INTENT_RELATE, question)
                }
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            question = it.getSerializable(INTENT_RELATE) as RelateData
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRelateBinding.bind(view)
        concatAdapter = ConcatAdapter()
        initQuestion(question)
    }

    private fun initQuestion(questions: RelateData) {
        initTitleQuestion("Relaciona bro")
        initAnswerQuestion(questions.answers)
    }

    private fun initTitleQuestion(title: String) {
        binding.tvQuestion.text = title
    }

    private fun initAnswerQuestion(answers: List<RelateAnswers>) {
        // binding.rvAnswer.layoutManager = LinearLayoutManager(requireContext())

        concatAdapter.apply {
            addAdapter(
                0,
                RelateLeftConcatAdapter(
                    RelateAdapter(answers) { relateDataAnswers, position ->
                        onAnswerSelected(
                            relateDataAnswers,
                            position
                        )
                    }
                )
            )
            addAdapter(
                1,
                RelateRightConcatAdapter(
                    RelateAdapter(answers) { relateDataAnswers, position ->
                        onAnswerSelected(
                            relateDataAnswers,
                            position
                        )
                    }
                )
            )
        }
        binding.rvAnswer.adapter = concatAdapter
        /*adapter = AnswerAdapter(answers) { answerData, position ->  onAnswerSelected(answerData, position)}
        binding.rvAnswer.adapter = adapter*/
    }

    private fun onAnswerSelected(result: RelateAnswers, position: Int) {
        val viewHolder = binding.rvAnswer.findViewHolderForAdapterPosition(position)
        //checkIsCorrect(result, (viewHolder  as AnswerViewHolder).btnAnswer)

        println("Apretado")

        listener?.onAnswerClickedRelate(result)
    }

    private fun checkIsCorrect(answer: RelateAnswers, button: Button) {
        /*if(answer.isCorrect){
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
        }else{
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
        }*/
//        disableAnswer()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRelateButtonActionListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}
package com.upitnik.playwithlessons.ui.questions

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.subject.Subject
import com.upitnik.playwithlessons.databinding.FragmentResultQuestionBinding

class ResultQuestionFragment : Fragment(R.layout.fragment_result_question) {

    private lateinit var binding: FragmentResultQuestionBinding

    companion object {

        const val INTENT_count = "count"
        const val INTENT_sizeList = "sizeList"
        const val INTENT_NumberLevel = "NumberLevel"
        const val INTENT_Subject = "Subject"

        @JvmStatic
        fun newInstance(count: Int, sizeList: Int, level: Int, Subject: Subject) =
            ResultQuestionFragment().apply {
                arguments = Bundle().apply {
                    putInt(INTENT_count, count)
                    putInt(INTENT_sizeList, sizeList)
                    putInt(INTENT_NumberLevel, level)
                    putSerializable(INTENT_Subject, Subject)
                }
            }
    }

    private var count: Int = 0
    private var sizeList: Int = 0
    private var level: Int = 0
    private var subject: Subject = Subject(0, 0, "", "", 0, 0, 0, "")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentResultQuestionBinding.bind(view)
        arguments?.apply {
            count = getInt(INTENT_count)
            sizeList = getInt(INTENT_sizeList)
            level = getInt(INTENT_NumberLevel)
            subject = getSerializable(INTENT_Subject) as Subject
        }
        binding.tvNumberAcertadas.text = count.toString()
        binding.tvNumberFalladas.text = (sizeList - count).toString()
        binding.btnBack.setOnClickListener {
            activity?.finish()
        }
        binding.btnGoMore.setOnClickListener {
            val intent = Intent(ActivityQuestions.create(activity?.baseContext!!))
            intent.putExtra("NumberLevel", level + 1)
            intent.putExtra("Subject", subject)
            startActivity(intent)
        }
    }

}
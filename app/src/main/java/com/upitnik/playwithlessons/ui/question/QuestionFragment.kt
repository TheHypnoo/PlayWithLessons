package com.upitnik.playwithlessons.ui.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.databinding.FragmentQuestionBinding
import com.upitnik.playwithlessons.ui.question.answer.AnswerAdapter

class QuestionFragment : Fragment() {
    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!
    //private var listener: OnQuestionActionListener? = null

    lateinit var adapter: AnswerAdapter

    lateinit var question: QuestionData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }
}
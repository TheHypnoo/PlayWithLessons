package com.upitnik.playwithlessons.ui.questions

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.core.content.ContextCompat
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
        formatResults()

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

    private fun formatResults() {
        val color: Int = if (count == 0) {
            ContextCompat.getColor(activity as Context, R.color.RojoNaranjao)
        } else {
            ContextCompat.getColor(activity as Context, R.color.darkGreen)
        }
        binding.tvAcertadas.text = "Acertadas: ${count}"
        val partsTvAcertadas = binding.tvAcertadas.text.split(":").toMutableList()
        partsTvAcertadas[0] = partsTvAcertadas[0] + ":"
        val spannableTvAcertadas =
            SpannableString("${partsTvAcertadas[0]} ${partsTvAcertadas[1].trim()}")

        spannableTvAcertadas.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(activity as Context, R.color.darkGreen)),
            partsTvAcertadas[0].length, partsTvAcertadas[0].length + partsTvAcertadas[1].length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableTvAcertadas.setSpan(
            StyleSpan(Typeface.BOLD),
            partsTvAcertadas[0].length,
            partsTvAcertadas[0].length + partsTvAcertadas[1].length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvAcertadas.text = spannableTvAcertadas

        binding.tvFallados.text = "Fallados: ${(sizeList - count)}"
        val partsTvFallados = binding.tvFallados.text.split(":").toMutableList()
        partsTvFallados[0] = partsTvFallados[0] + ":"
        val spannableTvFallados =
            SpannableString("${partsTvFallados[0]} ${partsTvFallados[1].trim()}")

        spannableTvFallados.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(activity as Context, R.color.RojoNaranjao)),
            partsTvFallados[0].length, partsTvFallados[0].length + partsTvFallados[1].length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableTvFallados.setSpan(
            StyleSpan(Typeface.BOLD),
            partsTvFallados[0].length,
            partsTvFallados[0].length + partsTvFallados[1].length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvFallados.text = spannableTvFallados

        binding.tvExperiencia.text = "Experiencia: +${15 * count}"
        val partsTvExperiencia = binding.tvExperiencia.text.split(":").toMutableList()
        partsTvExperiencia[0] = partsTvExperiencia[0] + ":"
        val spannableTvExperiencia =
            SpannableString("${partsTvExperiencia[0]} ${partsTvExperiencia[1].trim()}")

        spannableTvExperiencia.setSpan(
            ForegroundColorSpan(color),
            partsTvExperiencia[0].length,
            partsTvExperiencia[0].length + partsTvExperiencia[1].length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableTvExperiencia.setSpan(
            StyleSpan(Typeface.BOLD),
            partsTvExperiencia[0].length,
            partsTvExperiencia[0].length + partsTvExperiencia[1].length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvExperiencia.text = spannableTvExperiencia

        binding.tvPuntuacion.text = "Puntuación: +${10 * count}"

        val partsTvPuntuacion = binding.tvPuntuacion.text.split(":").toMutableList()
        partsTvPuntuacion[0] = partsTvPuntuacion[0] + ":"
        val spannableTvPuntuacion =
            SpannableString("${partsTvPuntuacion[0]} ${partsTvPuntuacion[1].trim()}")

        spannableTvPuntuacion.setSpan(
            ForegroundColorSpan(color),
            partsTvPuntuacion[0].length,
            partsTvPuntuacion[0].length + partsTvPuntuacion[1].length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableTvPuntuacion.setSpan(
            StyleSpan(Typeface.BOLD),
            partsTvPuntuacion[0].length,
            partsTvPuntuacion[0].length + partsTvPuntuacion[1].length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvPuntuacion.text = spannableTvPuntuacion

    }

}
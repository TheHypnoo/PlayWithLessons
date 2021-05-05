package com.upitnik.playwithlessons.ui.Subjects

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.subject.Subject
import com.upitnik.playwithlessons.databinding.FragmentSubjectBinding

class SubjectFragment : Fragment(R.layout.fragment_subject), OnSubjectActionListener {
    private lateinit var binding: FragmentSubjectBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSubjectBinding.bind(view)
        binding.rvSubject.layoutManager = LinearLayoutManager(this@SubjectFragment.context)
        binding.rvSubject.addItemDecoration(
            DividerItemDecoration(
                this@SubjectFragment.context,
                DividerItemDecoration.VERTICAL
            )
        )
        //Debo cargar todas las asignaturas desde la api y poner en cada asignatura su progreso, su nombre y imagen
        val Matematicas = Subject("Matematicas", 0)
        val Catalan = Subject("Catalan", 25)
        val Ingles = Subject("Ingles", 75)
        val Naturales = Subject("Naturales", 50)
        var listSubject: List<Subject> = listOf(Matematicas, Catalan, Ingles, Naturales)
        binding.rvSubject.adapter = SubjectAdapter(listSubject, this@SubjectFragment)
    }

    override fun onSubjectClicked(Subject: Subject) {
        binding.root.findNavController()
            .navigate(R.id.action_menuSelectSubject_to_menulevels)
    }

}
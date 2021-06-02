package com.upitnik.playwithlessons.ui.levels

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.core.extensions.gone
import com.upitnik.playwithlessons.core.extensions.invisible
import com.upitnik.playwithlessons.core.extensions.visible
import com.upitnik.playwithlessons.data.model.subject.Subject
import com.upitnik.playwithlessons.data.remote.levels.LevelsDataSource
import com.upitnik.playwithlessons.databinding.FragmentMenuLevelsBinding
import com.upitnik.playwithlessons.domain.levels.LevelsRepoImpl
import com.upitnik.playwithlessons.presentation.levels.LevelsViewModel
import com.upitnik.playwithlessons.presentation.levels.LevelsViewModelFactory

class MenuLevelsFragment : Fragment(R.layout.fragment_menu_levels) {

    private lateinit var binding: FragmentMenuLevelsBinding
    private val levelsViewModel by viewModels<LevelsViewModel> {
        LevelsViewModelFactory(
            LevelsRepoImpl(
                LevelsDataSource()
            )
        )
    }
    private var subject: Subject? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuLevelsBinding.bind(view)
        getLevels()
    }

    fun getLevels() {
        levelsViewModel.getLevels(subject!!.id).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbSelectLevels.visible()
                    binding.rvLevels.invisible()
                }
                is Result.Sucess -> {
                    binding.pbSelectLevels.gone()
                    binding.rvLevels.visible()
                    if (result.data.isNotEmpty()) {
                        binding.rvLevels.layoutManager =
                            GridLayoutManager(this@MenuLevelsFragment.context, 5)
                        val adapter = LevelsAdapter(result.data, subject!!)
                        binding.rvLevels.adapter = adapter
                        val controller =
                            AnimationUtils.loadLayoutAnimation(
                                binding.root.context,
                                R.anim.grid_layout_anim
                            )
                        binding.rvLevels.layoutAnimation = controller
                        adapter.notifyDataSetChanged()
                        binding.rvLevels.scheduleLayoutAnimation()
                    }
                }
                is Result.Failure -> {
                    binding.pbSelectLevels.visible()
                    binding.rvLevels.invisible()
                    dialogError(result.exception.toString())
                    /*Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()*/
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        subject = arguments?.getSerializable("Subject") as Subject?
    }

    override fun onResume() {
        super.onResume()
        getLevels()
    }

    private fun dialogError(message: String) {
        val view = View.inflate(binding.root.context, R.layout.dialog_error, null)

        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnConfirm = view.findViewById<Button>(R.id.btn_leave)
        btnConfirm.setOnClickListener {
            dialog.dismiss()
        }
        val textMessage = view.findViewById<TextView>(R.id.textMessage)
        textMessage.text = message
    }

}
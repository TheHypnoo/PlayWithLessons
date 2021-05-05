package com.upitnik.playwithlessons.ui.ranking

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.auth.User
import com.upitnik.playwithlessons.databinding.FragmentRankingBinding


class RankingFragment : Fragment(R.layout.fragment_ranking) {

    private lateinit var binding: FragmentRankingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRankingBinding.bind(view)
        val TheHypnoo = User(
            "tonto@gmail.com",
            "TheHypnoo",
            "https://www.ecestaticos.com/imagestatic/clipping/0cb/784/0cb784d3e9676df636b5ddd1b937c45a.jpg",
            100
        )
        val Deivid = User(
            "tonto@gmail.com",
            "Dedhon4",
            "https://m.media-amazon.com/images/I/71jod9LB42L._SS500_.jpg",
            95
        )
        val listUsers: List<User> = listOf(TheHypnoo, Deivid)
        binding.rvRanking.adapter = RankingAdapter(listUsers)
    }


}
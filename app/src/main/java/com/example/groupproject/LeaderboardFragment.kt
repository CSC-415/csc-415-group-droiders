package com.example.groupproject

import com.example.groupproject.data.leaderboard.ScoresData

import com.example.groupproject.R.layout.leaderboard
import com.example.groupproject.Game
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.groupproject.MainActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.groupproject.databinding.MainMenuBinding

class LeaderboardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.leaderboard, container, false)
    }

    init {
        val newScore = 0
        val scoresData = context?.let { ScoresData(newScore, it) }
        val scoresArr = scoresData?.getScoresArr()

        for (i in 0 until 5) {
            println("${i + 1}. ${scoresArr?.get(i)}")
        }
    }
}
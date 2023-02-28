package com.example.groupproject


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dinoImageView = findViewById<ImageView>(R.id.dino)


        Glide
            .with(this)
            .load(R.drawable.trex)
            .into(dinoImageView)

        val scoreTextView = findViewById<TextView>(R.id.current_score)
        scoreTextView.text = getString(R.string.scoreBeforeStart)

        val hiscoreTextView = findViewById<TextView>(R.id.high_score)
        hiscoreTextView.text=getString(R.string.hiscore) //later would update score


    }

    override fun onStart() {
        super.onStart()

        /*
        potential logic for handling running total
        var count = 0
        count++
        val scoreTextView = findViewById<TextView>(R.id.current_score)
        scoreTextView.text = "Count: $count"
        */

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
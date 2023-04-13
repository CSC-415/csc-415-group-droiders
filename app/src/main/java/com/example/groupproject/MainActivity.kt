package com.example.groupproject


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {
    private lateinit var gameView: GameView
    private lateinit var scoreTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get a reference to the GameView
        gameView = findViewById(R.id.game_view)

        // Get a reference to the score TextView
        scoreTextView = findViewById(R.id.current_score)
        scoreTextView.text = getString(R.string.scoreBeforeStart)

        // Other code for setting up the UI
    }

    override fun onStart() {
        super.onStart()

        // Register a callback to the 98GameView to update the score
        gameView.setOnScoreUpdateListener(this@MainActivity::updateScore)

        gameView.resume()
    }
    private fun updateScore(score: Int) {
        runOnUiThread {
            scoreTextView.text = "Score: $score"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
       gameView.pause()
    }
}

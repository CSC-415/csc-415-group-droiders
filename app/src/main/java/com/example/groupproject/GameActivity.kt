package com.example.groupproject

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GameActivity : AppCompatActivity() {
    private var game: Game? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        game = Game(this)
        setContentView(game)
    }
}
package com.example.groupproject


import AudioPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.groupproject.databinding.MainMenuBinding


class MainActivity : AppCompatActivity() {
    private var game: Game? = null
    private var audioPlayer: AudioPlayer = AudioPlayer(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val playButton = binding.play
        playButton.setOnClickListener(View.OnClickListener {
            game = Game(this)
            setContentView(game)
            audioPlayer.playAudio()
        })
        val optionsButton = binding.options
        optionsButton.setOnClickListener(View.OnClickListener {
            setContentView(R.layout.fragment_options_menu)
        })
    }

    override fun onStart() {
        super.onStart()


    }


    override fun onDestroy() {
        super.onDestroy()
    }
}

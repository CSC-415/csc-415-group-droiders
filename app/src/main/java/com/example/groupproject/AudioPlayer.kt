import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class AudioPlayer(private val context: Context) {
    val uri: Uri = Uri.parse("https://vgmsite.com/soundtracks/gain-ground-1991-genesis/qxypzawpvx/01%20-%20Genesis%20~Causing~%20%28Brave%20Men%27s%20Themes%29.mp3")

    private var mediaPlayer = MediaPlayer.create(context, uri)

    fun playAudio() {
        stopAudio()
        mediaPlayer?.setOnCompletionListener { stopAudio() }
        mediaPlayer?.start()

    }
    fun stopAudio() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}

package kz.mentalmind.ui.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerControlView
import kz.mentalmind.R
import kz.mentalmind.domain.dto.MeditationDto
import kz.mentalmind.utils.Constants.MEDITATION

class PlayerActivity : AppCompatActivity() {
    private var exoPlayer: SimpleExoPlayer? = null
    private var soundsPlayer: SimpleExoPlayer? = null
    private var playerView: PlayerControlView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val meditation: MeditationDto? = intent?.getParcelableExtra(MEDITATION)
        meditation?.let {
            findViewById<AppCompatTextView>(R.id.title).text = it.name
            findViewById<AppCompatTextView>(R.id.description).text = it.description
            initializePlayer(it)
        }
//        sea?.setOnClickListener {
//            soundsPlayer?.setMediaItem(MediaItem.fromUri("file:///android_asset/0eb24fcad1bffed.mp3"))
//        }
//        birds.setOnClickListener {
//            soundsPlayer?.setMediaItem(MediaItem.fromUri("file:///android_asset/544537.mp3"))
//        }
//        italy?.setOnClickListener {
//            soundsPlayer?.setMediaItem(MediaItem.fromUri("file:///android_asset/e55bd2beca1a2a1.mp3"))
//        }
//
//        volumeBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
//                soundsPlayer?.volume = when (p1) {
//                    0 -> 0f
//                    100 -> 1f
//                    else -> p1.toFloat() / 100.toFloat()
//                }
//                Log.d("yel", "sounds volume ${soundsPlayer?.volume}")
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {
//            }
//
//            override fun onStopTrackingTouch(p0: SeekBar?) {
//            }
//        })
    }

    override fun onDestroy() {
        releasePlayers()
        super.onDestroy()
    }

    private fun initializePlayer(meditation: MeditationDto) {
        exoPlayer = SimpleExoPlayer.Builder(this).build()
        exoPlayer?.apply {
            setMediaItem(MediaItem.fromUri(meditation.file_female_voice))
            playWhenReady = true
            seekTo(0, 0)
            prepare()
        }
        playerView = findViewById(R.id.exo_player_view)
        playerView?.setBackgroundColor(getColor(android.R.color.white))
        playerView?.player = exoPlayer

        soundsPlayer = SimpleExoPlayer.Builder(this)
            .build()
        soundsPlayer?.apply {
            repeatMode = Player.REPEAT_MODE_ONE
            playWhenReady = true
            volume = 0.5f
            seekTo(0, 0)
            prepare()
        }
    }

    private fun releasePlayers() {
        exoPlayer?.release()
        exoPlayer = null

        soundsPlayer?.release()
        soundsPlayer = null
    }
}
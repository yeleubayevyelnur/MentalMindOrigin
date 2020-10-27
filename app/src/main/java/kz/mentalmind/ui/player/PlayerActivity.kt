package kz.mentalmind.ui.player

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerControlView
import kotlinx.android.synthetic.main.activity_player.*
import kz.mentalmind.R
import kz.mentalmind.domain.dto.MeditationDto
import kz.mentalmind.utils.Constants.MEDITATION

class PlayerActivity : AppCompatActivity() {
    private val backgroundMusicPathFormat = "file:///android_asset/%s"
    private var exoPlayer: SimpleExoPlayer? = null
    private var soundsPlayer: SimpleExoPlayer? = null
    private var playerView: PlayerControlView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val meditation: MeditationDto? = intent?.getParcelableExtra(MEDITATION)
        meditation?.let {
            initializePlayer(it)

            findViewById<AppCompatTextView>(R.id.title).text = it.name
            findViewById<AppCompatTextView>(R.id.description).text = it.description
            backgroundMusic.setOnClickListener {
                settingsView.visibility = View.VISIBLE
            }

            speakerVoice.setOnClickListener {
                settingsView.visibility = View.VISIBLE
            }

            close.setOnClickListener {
                settingsView.visibility = View.GONE
            }

            back.setOnClickListener {
                onBackPressed()
            }

            volumeBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    soundsPlayer?.volume = when (p1) {
                        0 -> 0f
                        100 -> 1f
                        else -> p1.toFloat() / 100.toFloat()
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            })

            bg_musics.setOnCheckedChangeListener { _, checkedId ->
                val radioButton = findViewById<RadioButton>(checkedId)
                soundsPlayer?.setMediaItem(
                    MediaItem.fromUri(
                        String.format(
                            backgroundMusicPathFormat,
                            radioButton.tag
                        )
                    )
                )
                backgroundMusicTitle.text = radioButton.text
            }

            findViewById<RadioGroup>(R.id.bg_musics).check(R.id.bg_music1)
        }
    }

    override fun onDestroy() {
        releasePlayers()
        super.onDestroy()
    }

    private fun initializePlayer(meditation: MeditationDto) {
        exoPlayer = SimpleExoPlayer.Builder(this).build()
        exoPlayer?.apply {
            setMediaItem(MediaItem.fromUri(meditation.file_male_voice))
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

    override fun onBackPressed() {
        if (settingsView.visibility == View.VISIBLE) {
            settingsView.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }
}
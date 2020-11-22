package kz.mentalmind.ui.player

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerControlView
import kotlinx.android.synthetic.main.activity_player.*
import kz.mentalmind.R
import kz.mentalmind.data.dto.MeditationDto
import kz.mentalmind.utils.Constants.COLLECTION_ID
import kz.mentalmind.utils.Constants.MEDITATION
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerActivity : AppCompatActivity() {
    private val backgroundMusicPathFormat = "file:///android_asset/%s"
    private var exoPlayer: SimpleExoPlayer? = null
    private var soundsPlayer: SimpleExoPlayer? = null
    private var playerView: PlayerControlView? = null
    private val viewModel: PlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val meditation: MeditationDto? = intent?.getParcelableExtra(MEDITATION)
        meditation?.let { meditationDto ->
            initializePlayer()
            initView(meditationDto)
            setUpVoices(meditationDto)
            findViewById<RadioGroup>(R.id.bg_musics).check(R.id.bg_music1)
            speakerFemale.performClick()
        }
    }

    private fun initView(meditationDto: MeditationDto) {
        findViewById<AppCompatTextView>(R.id.title).text = meditationDto.name
        findViewById<AppCompatTextView>(R.id.description).text = meditationDto.description
        backgroundMusic.setOnClickListener {
            settingsView.visibility = View.VISIBLE
        }

        speakerVoice.setOnClickListener {
            settingsView.visibility = View.VISIBLE
        }

        close.setOnClickListener {
            settingsView.visibility = View.GONE
        }

        closeRateView.setOnClickListener {
            rateView.visibility = View.GONE
        }

        back.setOnClickListener {
            onBackPressed()
        }

        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            viewModel.setRating(meditationDto.id, rating.toInt())
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

        val favorite = findViewById<AppCompatImageView>(R.id.favorite)
        favorite.setOnClickListener {
            val collectionId = intent.getIntExtra(COLLECTION_ID, 0)
            viewModel.addToFavorites(meditationDto.id, collectionId)
            Glide.with(this).load(R.drawable.ic_favorite).into(favorite)
        }
    }

    private fun setUpVoices(meditationDto: MeditationDto) {
        when {
            meditationDto.file_male_voice.isNullOrEmpty() -> speakerMale.visibility = View.GONE
            meditationDto.file_female_voice.isNullOrEmpty() -> speakerFemale.visibility = View.GONE
        }

        speakerFemale.setOnClickListener {
            play(meditationDto, SPEAKER.FEMALE)
            speakerFemale.isSelected = true
            speakerMale.isSelected = false
            speakerVoiceTitle.text = speakerFemale.text
        }

        speakerMale.setOnClickListener {
            play(meditationDto, SPEAKER.MALE)
            speakerMale.isSelected = true
            speakerFemale.isSelected = false
            speakerVoiceTitle.text = speakerMale.text
        }
    }

    override fun onDestroy() {
        releasePlayers()
        super.onDestroy()
    }

    private fun initializePlayer() {
        exoPlayer = SimpleExoPlayer.Builder(this).build()
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
        exoPlayer?.addListener(object : Player.EventListener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    if (soundsPlayer?.isPlaying == false) {
                        soundsPlayer?.play()
                    }
                } else {
                    if (exoPlayer?.playbackState == Player.STATE_ENDED) {
                        rateView.visibility = View.VISIBLE
                    }
                    soundsPlayer?.pause()
                }
            }
        })
    }

    private fun play(meditation: MeditationDto, speaker: SPEAKER) {
        exoPlayer?.apply {
            setMediaItem(
                MediaItem.fromUri(
                    when (speaker) {
                        SPEAKER.MALE -> meditation.file_male_voice.orEmpty()
                        SPEAKER.FEMALE -> meditation.file_female_voice.orEmpty()
                    }
                )
            )
            playWhenReady = true
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

    enum class SPEAKER {
        MALE, FEMALE
    }
}
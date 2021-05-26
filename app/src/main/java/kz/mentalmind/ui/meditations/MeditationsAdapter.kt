package kz.mentalmind.ui.meditations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.mentalmind.R
import kz.mentalmind.data.dto.Meditation

class MeditationsAdapter(
    private var meditations: List<Meditation>,
    private val meditationClickListener: MeditationClickListener
) :
    RecyclerView.Adapter<MeditationsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MeditationsAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_audio_track, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: MeditationsAdapter.ViewHolder, position: Int) {
        val meditation = meditations[position]
        if (!isAvailable(meditation)) {
            Glide.with(holder.itemView)
                .load(R.drawable.ic_lock)
                .into(holder.controlView)
        }
        holder.title.text = meditation.name
        holder.duration.text = String.format(
            "%s %s",
            meditation.duration_female / 60,
            holder.itemView.context.getString(R.string.minute)
        )
        holder.itemView.setOnClickListener {
            meditationClickListener.onMeditationClicked(meditation)
        }
    }

    private fun isAvailable(meditation: Meditation) =
        !meditation.file_female_voice.isNullOrEmpty() || !meditation.file_male_voice.isNullOrEmpty()

    override fun getItemCount(): Int {
        return meditations.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: AppCompatTextView = itemView.findViewById(R.id.title)
        val duration: AppCompatTextView = itemView.findViewById(R.id.duration)
        val controlView: AppCompatImageView = itemView.findViewById(R.id.controlView)
    }
}
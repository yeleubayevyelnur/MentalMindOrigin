package kz.mentalmind.ui.meditations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import kz.mentalmind.R
import kz.mentalmind.data.dto.MeditationDto

class MeditationsAdapter(
    private var meditations: List<MeditationDto>,
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
        holder.title.text = meditation.name
        holder.duration.text = String.format(
            "%s %s",
            meditation.duration / 60,
            holder.itemView.context.getString(R.string.minute)
        )
        holder.itemView.setOnClickListener {
            meditationClickListener.onMeditationClicked(meditation)
        }
    }

    override fun getItemCount(): Int {
        return meditations.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: AppCompatTextView = itemView.findViewById(R.id.title)
        val duration: AppCompatTextView = itemView.findViewById(R.id.duration)
    }
}
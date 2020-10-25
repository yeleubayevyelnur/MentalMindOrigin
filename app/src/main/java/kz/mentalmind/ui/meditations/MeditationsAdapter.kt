package kz.mentalmind.ui.meditations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import kz.mentalmind.R
import kz.mentalmind.domain.dto.MeditationDto

class MeditationsAdapter(private var meditations: List<MeditationDto>) :
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
        holder.title.text = meditations[position].name
        holder.duration.text = String.format(
            "%s %s",
            meditations[position].duration / 60,
            holder.itemView.context.getString(R.string.minute)
        )
    }

    override fun getItemCount(): Int {
        return meditations.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: AppCompatTextView = itemView.findViewById(R.id.title)
        val duration: AppCompatTextView = itemView.findViewById(R.id.duration)
    }
}
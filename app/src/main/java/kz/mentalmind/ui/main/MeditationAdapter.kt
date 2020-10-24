package kz.mentalmind.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kz.mentalmind.R
import kz.mentalmind.data.CollectionItem

class MeditationAdapter(
    private var meditations: ArrayList<CollectionItem>,
    private val clickListener: MeditationClickListener
) :
    RecyclerView.Adapter<MeditationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_meditation, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meditation = meditations[position]
        holder.title.text = meditation.name
        holder.description.text = meditation.description
        holder.itemView.setOnClickListener {
            clickListener.onMeditationClicked(meditation)
        }
        Glide.with(holder.itemView)
            .load(meditation.file_image)
            .transform(RoundedCorners(15))
            .into(holder.banner)
    }

    override fun getItemCount(): Int {
        return meditations.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val description: TextView = itemView.findViewById(R.id.tvDescription)
        val banner: ImageView = itemView.findViewById(R.id.ivBanner)
    }
}
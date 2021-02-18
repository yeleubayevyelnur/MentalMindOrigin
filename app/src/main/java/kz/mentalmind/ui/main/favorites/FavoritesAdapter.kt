package kz.mentalmind.ui.main.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kz.mentalmind.R
import kz.mentalmind.data.dto.FavoriteMeditation
import kz.mentalmind.utils.dpToPixelInt

class FavoritesAdapter(
    private var instruments: List<FavoriteMeditation>,
    private val clickListener: FavoriteClickListener
) :
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_instrument, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meditation = instruments[position]
        holder.title.text = meditation.meditation_name
        holder.description.text = meditation.meditation_description
        holder.itemView.setOnClickListener {
            clickListener.onFavoriteClicked(meditation)
        }
        Glide.with(holder.itemView)
            .load(meditation.file_image)
            .placeholder(R.drawable.ic_placeholder_instrument)
            .transform(RoundedCorners(holder.itemView.dpToPixelInt(15f)))
            .into(holder.banner)
    }

    override fun getItemCount(): Int {
        return instruments.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val description: TextView = itemView.findViewById(R.id.tvDescription)
        val banner: ImageView = itemView.findViewById(R.id.ivBanner)
    }
}
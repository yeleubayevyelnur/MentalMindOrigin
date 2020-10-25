package kz.mentalmind.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.mentalmind.R
import kz.mentalmind.data.profile.LevelsResult

class LevelsAdapter(private var levels: ArrayList<LevelsResult>) :
    RecyclerView.Adapter<LevelsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_level, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.label.text = levels[position].label
        Glide.with(holder.itemView).load(levels[position].file_image).into(holder.ivLevel)
        holder.tvLevel.text = levels[position].name
        holder.countDay.text = String.format("%s дней", levels[position].days_with_us.toString())
        holder.countTime.text = String.format("%s минут", levels[position].listened_minutes.toString())
    }

    override fun getItemCount(): Int {
        return levels.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val label: TextView = itemView.findViewById(R.id.label)
        val ivLevel: ImageView = itemView.findViewById(R.id.ivLevel)
        val tvLevel: TextView = itemView.findViewById(R.id.tvLevel)
        val countDay: TextView = itemView.findViewById(R.id.countDay)
        val countTime: TextView = itemView.findViewById(R.id.countTime)
    }
}
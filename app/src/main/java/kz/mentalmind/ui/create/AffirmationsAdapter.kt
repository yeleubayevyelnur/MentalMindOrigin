package kz.mentalmind.ui.create

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kz.mentalmind.R
import kz.mentalmind.data.dto.Affirmation
import kz.mentalmind.utils.dpToPixelInt

class AffirmationsAdapter(
    private var affirmations: List<Affirmation>,
    private val clickListener: AffirmationClickListener
) :
    RecyclerView.Adapter<AffirmationsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_affirmation, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val affirmation = affirmations[position]
        holder.title.text = affirmation.name
        holder.itemView.setOnClickListener {
            clickListener.onAffirmationClicked(affirmation)
        }
        Glide.with(holder.itemView)
            .load(affirmation.file_image)
            .placeholder(R.drawable.ic_placeholder_instrument)
            .transform(RoundedCorners(holder.itemView.dpToPixelInt(15f)))
            .into(holder.banner)
    }

    override fun getItemCount(): Int {
        return affirmations.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: AppCompatTextView = itemView.findViewById(R.id.tvTitle)
        val banner: AppCompatImageView = itemView.findViewById(R.id.ivBanner)
    }
}
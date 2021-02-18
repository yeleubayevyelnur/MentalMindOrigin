package kz.mentalmind.ui.main.challenges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kz.mentalmind.R
import kz.mentalmind.data.dto.Challenge
import kz.mentalmind.utils.dpToPixelInt

class ChallengesAdapter(
    private val challenges: List<Challenge>,
    private val clickListener: ChallengeClickListener
) :
    RecyclerView.Adapter<ChallengesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_challenge, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val challenge = challenges[position]
        holder.title.text = challenge.name
        holder.description.text = challenge.description
        holder.challengeName.text = challenge.name
        holder.itemView.setOnClickListener {
            clickListener.onChallengeClicked(challenge)
        }
        Glide.with(holder.itemView)
            .load(challenge.file_image)
            .transform(RoundedCorners(holder.itemView.dpToPixelInt(15f)))
            .into(holder.banner)
    }

    override fun getItemCount(): Int {
        return challenges.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: AppCompatTextView = itemView.findViewById(R.id.tvTitle)
        val description: AppCompatTextView = itemView.findViewById(R.id.tvDescription)
        val banner: AppCompatImageView = itemView.findViewById(R.id.ivBanner)
        val challengeName: AppCompatTextView = itemView.findViewById(R.id.challengeName)
    }
}
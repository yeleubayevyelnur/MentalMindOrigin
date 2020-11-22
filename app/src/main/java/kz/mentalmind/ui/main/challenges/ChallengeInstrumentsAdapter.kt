package kz.mentalmind.ui.main.challenges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kz.mentalmind.R
import kz.mentalmind.data.dto.CollectionDto
import kz.mentalmind.ui.main.instruments.InstrumentClickListener
import kz.mentalmind.utils.dpToPixelInt

class ChallengeInstrumentsAdapter(
    private var instruments: List<CollectionDto>,
    private val clickListener: InstrumentClickListener
) :
    RecyclerView.Adapter<ChallengeInstrumentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_challenge_instrument, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val instrument = instruments[position]
        holder.title.text = instrument.name
        holder.description.text = instrument.description
        holder.itemView.setOnClickListener {
            clickListener.onInstrumentClicked(instrument)
        }
        Glide.with(holder.itemView)
            .load(instrument.file_image)
            .placeholder(R.drawable.ic_placeholder_challenge)
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
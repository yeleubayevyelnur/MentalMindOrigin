package kz.mentalmind.ui.instruments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kz.mentalmind.R
import kz.mentalmind.data.dto.Collection
import kz.mentalmind.ui.main.instruments.InstrumentClickListener
import kz.mentalmind.utils.dpToPixelInt

class InstrumentsAdapter1(
    private var instruments: List<Collection>,
    private val clickListener: InstrumentClickListener
) :
    RecyclerView.Adapter<InstrumentsAdapter1.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_instrument1, parent, false)
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
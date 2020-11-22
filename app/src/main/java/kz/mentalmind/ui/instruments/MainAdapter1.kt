package kz.mentalmind.ui.instruments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.mentalmind.R
import kz.mentalmind.data.dto.KeyValuePairDto
import kz.mentalmind.ui.main.instruments.InstrumentsAdapter

class MainAdapter1(
    private var tags: List<KeyValuePairDto>,
    private var instrumentsAdapters: List<Pair<Int, InstrumentsAdapter1>>
) :
    RecyclerView.Adapter<MainAdapter1.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter1.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_main_page, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainAdapter1.ViewHolder, position: Int) {
        holder.tagName.text = tags[position].name
        holder.instruments.adapter = instrumentsAdapters.first { it.first == tags[position].id }.second
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tagName: TextView = itemView.findViewById(R.id.tagName)
        val instruments: RecyclerView = itemView.findViewById(R.id.instruments)
    }
}
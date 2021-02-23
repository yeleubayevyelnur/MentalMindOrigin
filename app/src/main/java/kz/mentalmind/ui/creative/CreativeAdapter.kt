package kz.mentalmind.ui.creative

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.mentalmind.R
import kz.mentalmind.data.dto.KeyValuePair
import kz.mentalmind.ui.main.instruments.InstrumentsAdapter

class CreativeAdapter(
    private var types: List<KeyValuePair>,
    private var instrumentsAdapters: List<Pair<Int, InstrumentsAdapter>>
) :
    RecyclerView.Adapter<CreativeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_creation, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.typeName.text = types[position].name
        holder.instruments.adapter = instrumentsAdapters.first { it.first == types[position].id }.second
    }

    override fun getItemCount(): Int {
        return types.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val typeName: TextView = itemView.findViewById(R.id.typeName)
        val instruments: RecyclerView = itemView.findViewById(R.id.instruments)
    }
}
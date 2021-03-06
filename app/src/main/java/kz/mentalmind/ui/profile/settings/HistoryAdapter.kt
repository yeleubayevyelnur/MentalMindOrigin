package kz.mentalmind.ui.profile.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.mentalmind.R
import kz.mentalmind.data.MeditationResult

class HistoryAdapter(private val history: ArrayList<MeditationResult>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvHistory.text = history[position].meditation
    }

    override fun getItemCount(): Int {
        return history.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvHistory: TextView = itemView.findViewById(R.id.tvHistory)
    }
}
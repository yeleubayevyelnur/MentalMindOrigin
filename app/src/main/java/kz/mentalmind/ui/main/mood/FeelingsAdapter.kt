package kz.mentalmind.ui.main.mood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.mentalmind.R
import kz.mentalmind.data.Feeling

class FeelingsAdapter(private val feelingData: ArrayList<Feeling>) :
    RecyclerView.Adapter<FeelingsAdapter.ViewHolder>() {

    private lateinit var moodClickListener: MoodResultListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeelingsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_mood, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: FeelingsAdapter.ViewHolder, position: Int) {
        holder.ivMood.setImageDrawable(feelingData[position].icon)
        holder.tvMood.text = feelingData[position].name
        holder.llContainer.setOnClickListener {
            moodClickListener.onMoodClickedResult(feelingData[position])
        }
    }

    override fun getItemCount(): Int {
        return feelingData.size
    }

    fun setMoodResultListener(listener: MoodResultListener) {
        moodClickListener = listener
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val ivMood: ImageView = itemView.findViewById(R.id.ivMood)
        val tvMood: TextView = itemView.findViewById(R.id.tvMood)
        val llContainer: LinearLayout = itemView.findViewById(R.id.llContainer)
    }
}
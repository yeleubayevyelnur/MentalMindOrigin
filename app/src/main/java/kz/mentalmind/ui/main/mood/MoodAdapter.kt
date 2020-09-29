package kz.mentalmind.ui.main.mood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.mentalmind.R
import kz.mentalmind.data.MoodData

class MoodAdapter(private val moodData: ArrayList<MoodData>) :
    RecyclerView.Adapter<MoodAdapter.ViewHolder>() {

    private lateinit var moodClickListener: MoodResultListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_mood, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: MoodAdapter.ViewHolder, position: Int) {
        holder.ivMood.setImageDrawable(moodData[position].icon)
        holder.tvMood.text = moodData[position].name
        holder.llContainer.setOnClickListener {
            moodClickListener.onMoodClickedResult(moodData[position])
        }
    }

    override fun getItemCount(): Int {
        return moodData.size
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
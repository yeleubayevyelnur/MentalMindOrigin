package kz.mentalmind.ui.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.mentalmind.R
import kz.mentalmind.data.IntroSlide

class IntroSliderAdapter(private val introSlides: List<IntroSlide>): RecyclerView.Adapter<IntroSliderAdapter.IntroSliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSliderViewHolder {
        return IntroSliderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.slide_item_container, parent, false)
        )
    }

    override fun onBindViewHolder(holder: IntroSliderViewHolder, position: Int) {
        holder.bind(introSlides[position])
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }

    inner class IntroSliderViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val mainText: TextView = itemView.findViewById(R.id.tvMain)
        val secondaryText: TextView = itemView.findViewById(R.id.tvSecondary)

        fun bind(introSlide: IntroSlide){
            mainText.text = introSlide.mainText
            secondaryText.text = introSlide.secondaryText
        }
    }
}
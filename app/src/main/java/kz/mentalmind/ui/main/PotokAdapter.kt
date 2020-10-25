package kz.mentalmind.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.mentalmind.R
import kz.mentalmind.data.Page

class PotokAdapter(private var pageList: ArrayList<Page>) :
    RecyclerView.Adapter<PotokAdapter.ViewHolder>()  {

    private lateinit var instrumentClickListener: InstrumentClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PotokAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_instrument1, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: PotokAdapter.ViewHolder, position: Int) {
        holder.pageTitle.text = pageList[position].title
        holder.description.text = pageList[position].description
        holder.image.setImageDrawable(pageList[position].image)
        holder.llContainer.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return pageList.size
    }

    fun setItemResultListener(listener: InstrumentClickListener) {
        instrumentClickListener = listener
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val pageTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val description: TextView = itemView.findViewById(R.id.tvDescription)
        val image: ImageView = itemView.findViewById(R.id.ivBanner)
        val llContainer: LinearLayout = itemView.findViewById(R.id.llContainer)
    }
}
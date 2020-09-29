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

class ItemAdapter(private var pageList: ArrayList<Page>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private lateinit var itemResultListener: ItemResultListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_pages, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        holder.pageTitle.text = pageList[position].title
        holder.description.text = pageList[position].description
        holder.image.setImageDrawable(pageList[position].image)
        holder.llContainer.setOnClickListener {
            itemResultListener.onItemClickedResult(pageList[position])
        }
    }

    override fun getItemCount(): Int {
        return pageList.size
    }

    fun setItemResultListener(listener: ItemResultListener) {
        itemResultListener = listener
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val pageTitle: TextView = itemView.findViewById(R.id.tvPageTitle)
        val description: TextView = itemView.findViewById(R.id.tvPageDescription)
        val image: ImageView = itemView.findViewById(R.id.ivPages)
        val llContainer: LinearLayout = itemView.findViewById(R.id.llContainer)
    }
}
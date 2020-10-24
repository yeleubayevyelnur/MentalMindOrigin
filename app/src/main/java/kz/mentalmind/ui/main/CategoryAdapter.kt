package kz.mentalmind.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.mentalmind.R
import kz.mentalmind.data.Category

class CategoryAdapter(private var categoryList: ArrayList<Category>, private val mainAdapter: MainAdapter) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.title.text = categoryList[position].name
        holder.rvPages.adapter = mainAdapter
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = itemView.findViewById(R.id.tvCategory)
        val rvPages: RecyclerView = itemView.findViewById(R.id.rvPages)
    }
}
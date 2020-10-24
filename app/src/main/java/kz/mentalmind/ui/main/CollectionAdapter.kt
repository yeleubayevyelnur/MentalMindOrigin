package kz.mentalmind.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.mentalmind.data.CollectionResult
import kz.mentalmind.R

class CollectionAdapter(
    private var collections: ArrayList<CollectionResult>,
    private var tag: String
) :
    RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {

    private lateinit var itemResultListener: ItemResultListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_pages, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pageTitle.text = collections[position].name
        holder.description.text = collections[position].description
        holder.llContainer.setOnClickListener {
            itemResultListener.onItemClickedResult(collections[position])
        }
        Glide.with(holder.itemView).load(collections[position].file_image).into(holder.image)
    }

    override fun getItemCount(): Int {
        return collections.size
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

    fun setNewData(newData: ArrayList<CollectionResult>) {
        collections.clear()
        collections.addAll(newData.filter { it.tags.contains(tag) })
        notifyDataSetChanged()
    }
}
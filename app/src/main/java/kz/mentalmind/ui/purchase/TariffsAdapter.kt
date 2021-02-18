package kz.mentalmind.ui.purchase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kz.mentalmind.R
import kz.mentalmind.data.dto.Tariff

class TariffsAdapter(private var tariffs: List<Tariff>) :
    RecyclerView.Adapter<TariffsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TariffsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_tariff, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text = String.format(
            "%s %s",
            tariffs[position].price,
            holder.itemView.context.getString(R.string.tenge_symbol)
        )
        holder.description.text = tariffs[position].description
        holder.name.text = tariffs[position].name
        holder.card.background = ContextCompat.getDrawable(
            holder.itemView.context,
            R.drawable.ic_first_tariff_background
        )
    }

    override fun getItemCount(): Int {
        return tariffs.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val price: TextView = itemView.findViewById(R.id.price)
        val description: TextView = itemView.findViewById(R.id.description)
        val name: TextView = itemView.findViewById(R.id.name)
        val card: ConstraintLayout = itemView.findViewById(R.id.card)
    }
}
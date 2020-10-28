package kz.mentalmind.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.mentalmind.R
import kz.mentalmind.domain.dto.CourseDto

class CoursesAdapter(
    private var courses: List<CourseDto>,
    private val courseClickListener: CourseClickListener
) :
    RecyclerView.Adapter<CoursesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CoursesAdapter.ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(courses[position].file_image).into(holder.banner)
        holder.itemView.setOnClickListener {
            courseClickListener.onCourseClicked(courses[position])
        }
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val banner: AppCompatImageView = itemView.findViewById(R.id.banner)
    }
}
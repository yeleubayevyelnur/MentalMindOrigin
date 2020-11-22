package kz.mentalmind.ui.main.courses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kz.mentalmind.R
import kz.mentalmind.data.dto.CourseDto
import kz.mentalmind.utils.dpToPixelInt

class CoursesAdapter(
    private var courses: List<CourseDto>,
    private val courseClickListener: CourseClickListener
) :
    RecyclerView.Adapter<CoursesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(courses[position].file_image)
            .transform(RoundedCorners(holder.itemView.dpToPixelInt(15f)))
            .into(holder.banner)
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
package kz.mentalmind.ui.main.courses

import kz.mentalmind.data.dto.CourseDto

interface CourseClickListener {
    fun onCourseClicked(course: CourseDto)
}
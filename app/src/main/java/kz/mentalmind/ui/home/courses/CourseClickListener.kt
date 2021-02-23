package kz.mentalmind.ui.home.courses

import kz.mentalmind.data.dto.Course

interface CourseClickListener {
    fun onCourseClicked(course: Course)
}
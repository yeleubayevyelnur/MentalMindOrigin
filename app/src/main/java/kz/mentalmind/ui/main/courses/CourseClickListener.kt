package kz.mentalmind.ui.main.courses

import kz.mentalmind.data.dto.Course

interface CourseClickListener {
    fun onCourseClicked(course: Course)
}
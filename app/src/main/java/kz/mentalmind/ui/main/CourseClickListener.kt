package kz.mentalmind.ui.main

import kz.mentalmind.data.dto.CourseDto

interface CourseClickListener {
    fun onCourseClicked(course: CourseDto)
}
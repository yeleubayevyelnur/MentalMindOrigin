package kz.mentalmind.ui.main

import kz.mentalmind.domain.dto.CourseDto

interface CourseClickListener {
    fun onCourseClicked(course: CourseDto)
}
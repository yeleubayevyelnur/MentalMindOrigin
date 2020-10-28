package kz.mentalmind.domain.dto

data class CourseDto(val id: Int, val name: String, val url: String, val file_image: String)

data class CoursesData(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<CourseDto>
)
package kz.mentalmind.data

import com.google.gson.annotations.SerializedName

data class TagsResponse(
    @SerializedName("data")
    val tagsData: TagsData,
    val error: String? = null
)

data class TagsData(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: ArrayList<TagsResult>
)

data class TagsResult(
    val id: Int,
    val name: String
)
package kz.mentalmind.data

import com.google.gson.annotations.SerializedName

data class CollectionsResponse(
    @SerializedName("data")
    val collections: Collections,
    val error: String?
)

data class Collections(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: ArrayList<CollectionResult>
)

data class CollectionResult(
    val description: String,
    val file_image: String,
    val for_feeling: String,
    val id: Int,
    val name: String,
    val tags: ArrayList<String>,
    val type: String
)
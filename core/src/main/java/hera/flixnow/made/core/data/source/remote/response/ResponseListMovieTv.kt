package hera.flixnow.made.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseListMovieTv(
    @field:SerializedName("page")
    val page: Int?,

    @field:SerializedName("total_results")
    val total_results: Int?,

    @field:SerializedName("total_pages")
    val total_pages: Int?,

    @field:SerializedName("results")
    val results: List<ResponseMovieTv>?
)
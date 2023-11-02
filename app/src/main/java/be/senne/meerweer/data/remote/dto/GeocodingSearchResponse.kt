package be.senne.meerweer.data.remote.dto

data class GeocodingSearchResponse(
    val results: ArrayList<GeocodingSearchItem>?,

    val reason: String?,
    val error: Boolean?
)
package be.senne.meerweer.data.remote.dto

data class GeocodingSearchItem(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val elevation: Long,
    val feature_code: String,
    val country_code: String,
    val admin1_id: Long?,
    val admin2_id: Long?,
    val admin3_id: Long?,
    val admin4_id: Long?,
    val timeszone: String,
    val population: Long,
    val postcodes: ArrayList<String>?,
    val country_id: Long,
    val country: String,
    val admin1: String?,
    val admin2: String?,
    val admin3: String?,
    val admin4: String?,
)
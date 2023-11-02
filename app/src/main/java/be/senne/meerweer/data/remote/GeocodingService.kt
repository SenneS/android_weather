package be.senne.meerweer.data.remote

import be.senne.meerweer.data.remote.dto.GeocodingGetResponse
import be.senne.meerweer.data.remote.dto.GeocodingSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {
    companion object {
        const val BASE_URL = "https://geocoding-api.open-meteo.com/v1"
    }
    @GET("$BASE_URL/search")
    suspend fun searchLocation(@Query("name") name: String) : GeocodingSearchResponse

    @GET("$BASE_URL/get")
    suspend fun searchLocation(@Query("id") id: Long) : GeocodingGetResponse
}
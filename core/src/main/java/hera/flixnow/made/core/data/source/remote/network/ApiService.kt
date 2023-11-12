package hera.flixnow.made.core.data.source.remote.network

import hera.flixnow.made.core.BuildConfig
import hera.flixnow.made.core.data.source.remote.response.ResponseListMovieTv
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getMovies(
        @Query("api_key") apiKey: String? = BuildConfig.API_KEY
    ): ResponseListMovieTv

    @GET("tv/popular")
    suspend fun getTvShows(
        @Query("api_key") apiKey: String? = BuildConfig.API_KEY
    ): ResponseListMovieTv

    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("api_key") apiKey: String? = BuildConfig.API_KEY,
        @Query("query") query: String?
    ): ResponseListMovieTv

    @GET("search/tv")
    suspend fun getSearchTvShows(
        @Query("api_key") apiKey: String? = BuildConfig.API_KEY,
        @Query("query") query: String?
    ): ResponseListMovieTv
}

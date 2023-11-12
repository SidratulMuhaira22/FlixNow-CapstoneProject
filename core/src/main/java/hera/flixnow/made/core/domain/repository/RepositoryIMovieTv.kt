package hera.flixnow.made.core.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import hera.flixnow.made.core.data.Resource
import hera.flixnow.made.core.domain.model.MovieTvModel
import kotlinx.coroutines.flow.Flow

interface RepositoryIMovieTv {
    fun getMovies(): Flow<Resource<List<MovieTvModel>>>
    fun getTvShows(): Flow<Resource<List<MovieTvModel>>>
    fun getFavoriteMovies(): LiveData<PagedList<MovieTvModel>>
    fun getFavoriteTvShows(): LiveData<PagedList<MovieTvModel>>
    fun searchMovies(query: String): Flow<Resource<List<MovieTvModel>>>
    fun searchTvShows(query: String): Flow<Resource<List<MovieTvModel>>>
    fun setFavoriteMovieTv(movieTvModel: MovieTvModel, saved: Boolean)
    fun isFavorite(movieTvModel: MovieTvModel): Flow<Boolean>
}
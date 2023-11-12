package hera.flixnow.made.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import hera.flixnow.made.core.data.Resource
import hera.flixnow.made.core.domain.model.MovieTvModel
import hera.flixnow.made.core.domain.repository.RepositoryIMovieTv
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InteractorMovieTv @Inject constructor(private val tourismRepository: RepositoryIMovieTv):
    UseCaseMovieTv {

    override fun getMovies(): Flow<Resource<List<MovieTvModel>>> = tourismRepository.getMovies()

    override fun getTvShows(): Flow<Resource<List<MovieTvModel>>> = tourismRepository.getTvShows()

    override fun getFavoriteMovies(): LiveData<PagedList<MovieTvModel>> =
        tourismRepository.getFavoriteMovies()

    override fun getFavoriteTvShows(): LiveData<PagedList<MovieTvModel>> =
        tourismRepository.getFavoriteTvShows()

    override fun searchMovies(query: String): Flow<Resource<List<MovieTvModel>>> =
        tourismRepository.searchMovies(query)

    override fun searchTvShows(query: String): Flow<Resource<List<MovieTvModel>>> =
        tourismRepository.searchTvShows(query)

    override fun setFavoriteMovieTv(movieTvModel: MovieTvModel, saved: Boolean) =
        tourismRepository.setFavoriteMovieTv(movieTvModel, saved)

    override fun isFavorite(movieTvModel: MovieTvModel) = tourismRepository.isFavorite(movieTvModel)
}
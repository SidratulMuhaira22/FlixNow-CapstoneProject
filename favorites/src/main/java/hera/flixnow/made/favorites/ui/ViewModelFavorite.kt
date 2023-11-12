package hera.flixnow.made.favorites.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import hera.flixnow.made.core.domain.model.MovieTvModel
import hera.flixnow.made.core.domain.usecase.UseCaseMovieTv
import javax.inject.Inject

class ViewModelFavorite @Inject constructor(private val useCase: UseCaseMovieTv?) : ViewModel() {
    val favoriteMovies = useCase?.getFavoriteMovies()
    val favoriteTvShows = useCase?.getFavoriteTvShows()

    fun isFavorite(movieTvModel: MovieTvModel) = useCase?.isFavorite(movieTvModel)?.asLiveData()
    fun setToFavorite(movieTvModel: MovieTvModel, saved: Boolean) = useCase?.setFavoriteMovieTv(movieTvModel, saved)
}
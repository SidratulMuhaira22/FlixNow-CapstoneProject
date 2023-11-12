package hera.flixnow.made.submission.ui.components.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import hera.flixnow.made.core.domain.model.MovieTvModel
import hera.flixnow.made.core.domain.usecase.MovieTvUseCase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ViewModelMovie @Inject constructor(private val useCase: MovieTvUseCase?) : ViewModel() {

  val movies = useCase?.getMovies()?.asLiveData()

  val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)
  val search = queryChannel.asFlow()
    .debounce(1000)
    .distinctUntilChanged()
    .filter {
      it.trim().isNotEmpty()
    }
    .mapLatest {
      useCase?.searchMovies(it)
    }
    .asLiveData()

  fun isFavorite(movieTvModel: MovieTvModel) = useCase?.isFavorite(movieTvModel)?.asLiveData()
  fun setToFavorite(movieTvModel: MovieTvModel, saved: Boolean) = useCase?.setFavoriteMovieTv(movieTvModel, saved)
}
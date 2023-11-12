package hera.flixnow.made.submission.ui.components.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import hera.flixnow.made.core.domain.model.MovieTvModel
import hera.flixnow.made.core.domain.usecase.UseCaseMovieTv
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ViewModelTv @Inject constructor(private val useCase: UseCaseMovieTv?) : ViewModel() {

  val tvShows = useCase?.getTvShows()?.asLiveData()

  val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)
  val search = queryChannel.asFlow()
    .debounce(1000)
    .distinctUntilChanged()
    .filter {
      it.trim().isNotEmpty()
    }
    .mapLatest {
      useCase?.searchTvShows(it)
    }
    .asLiveData()

  fun isFavorite(movieTvModel: MovieTvModel) = useCase?.isFavorite(movieTvModel)?.asLiveData()
  fun setToFavorite(movieTvModel: MovieTvModel, state: Boolean) = useCase?.setFavoriteMovieTv(movieTvModel, state)
}
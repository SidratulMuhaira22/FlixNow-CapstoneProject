package hera.flixnow.made.submission.ui.components.detail

import androidx.lifecycle.*
import hera.flixnow.made.core.domain.model.MovieTvModel
import hera.flixnow.made.core.domain.usecase.MovieTvUseCase
import javax.inject.Inject

class ViewModelDetail @Inject constructor(private val useCase: MovieTvUseCase?) : ViewModel() {
    private val _movieTvModelItem = MutableLiveData<MovieTvModel>()
    val movieTvModelItem: LiveData<MovieTvModel> = _movieTvModelItem
    var isFavorite: LiveData<Boolean> = Transformations.switchMap(_movieTvModelItem) { item ->
        useCase?.isFavorite(item)?.asLiveData()
    }

    fun setSelectedItem(item: MovieTvModel) {
        _movieTvModelItem.postValue(item)
    }

    fun setToFavorite(saved: Boolean) {
        _movieTvModelItem.value?.let {
            useCase?.setFavoriteMovieTv(it, saved)
            _movieTvModelItem.postValue(it)
        }
    }
}
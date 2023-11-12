package hera.flixnow.made.submission.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hera.flixnow.made.submission.ui.FactoryViewModel
import hera.flixnow.made.submission.ui.components.detail.ViewModelDetail
import hera.flixnow.made.submission.ui.components.movie.ViewModelMovie
import hera.flixnow.made.submission.ui.components.tv.ViewModelTv

@Suppress("unused")
@Module
abstract class MyViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: FactoryViewModel): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @MyViewModelKey(ViewModelMovie::class)
    abstract fun bindMovieViewModel(viewModel: ViewModelMovie): ViewModel

    @Binds
    @IntoMap
    @MyViewModelKey(ViewModelTv::class)
    abstract fun bindTvViewModel(viewModel: ViewModelTv): ViewModel

    @Binds
    @IntoMap
    @MyViewModelKey(ViewModelDetail::class)
    abstract fun bindDetailViewModel(viewModel: ViewModelDetail): ViewModel
}
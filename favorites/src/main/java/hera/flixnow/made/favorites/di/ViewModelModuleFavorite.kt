package hera.flixnow.made.favorites.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hera.flixnow.made.submission.di.MyViewModelKey
import hera.flixnow.made.submission.ui.FactoryViewModel
import hera.flixnow.made.favorites.ui.ViewModelFavorite

@Suppress("unused")
@Module
abstract class ViewModelModuleFavorite {

    @Binds
    abstract fun bindFavoriteViewModelFactory(factory: FactoryViewModel): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @MyViewModelKey(ViewModelFavorite::class)
    abstract fun bindFavoriteViewModel(viewModel: ViewModelFavorite): ViewModel
}
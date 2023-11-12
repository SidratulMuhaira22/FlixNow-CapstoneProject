package hera.flixnow.made.favorites.di

import dagger.Component
import hera.flixnow.made.submission.di.MyAppModule
import hera.flixnow.made.core.di.ComponentCore
import hera.flixnow.made.favorites.ui.FragmentFavorite
import hera.flixnow.made.favorites.ui.base.BaseFragmentFavoriteMovie
import hera.flixnow.made.favorites.ui.base.BaseFragmentFavoriteTv

@AppScopeFavorite
@Component(
    dependencies = [ComponentCore::class],
    modules = [MyAppModule::class, ViewModelModuleFavorite::class]
)
interface ComponentFavorite {

    fun inject(fragment: FragmentFavorite)
    fun inject(fragment: BaseFragmentFavoriteMovie)
    fun inject(fragment: BaseFragmentFavoriteTv)
}
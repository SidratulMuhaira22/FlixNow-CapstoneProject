package hera.flixnow.made.submission.di

import dagger.Binds
import dagger.Module
import hera.flixnow.made.core.domain.usecase.InteractorMovieTvUseCase
import hera.flixnow.made.core.domain.usecase.MovieTvUseCase

@Module
abstract class MyAppModule {

    @Suppress("unused")
    @Binds
    abstract fun provideMovieUseCase(movieInteractor: InteractorMovieTvUseCase): MovieTvUseCase
}
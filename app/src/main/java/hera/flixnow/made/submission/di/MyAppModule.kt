package hera.flixnow.made.submission.di

import dagger.Binds
import dagger.Module
import hera.flixnow.made.core.domain.usecase.InteractorMovieTv
import hera.flixnow.made.core.domain.usecase.UseCaseMovieTv

@Module
abstract class MyAppModule {

    @Suppress("unused")
    @Binds
    abstract fun provideMovieUseCase(movieInteractor: InteractorMovieTv): UseCaseMovieTv
}
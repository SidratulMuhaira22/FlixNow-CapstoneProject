package hera.flixnow.made.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import hera.flixnow.made.core.data.source.local.room.MovieTvDaoFavorite
import hera.flixnow.made.core.data.source.local.room.MovieTvDao
import hera.flixnow.made.core.data.source.local.room.MovieTvDatabase
import javax.inject.Singleton

@Module
class ModuleDatabase {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): MovieTvDatabase = Room.databaseBuilder(
        context,
        MovieTvDatabase::class.java, "MovieTvLocal.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideMovieTvDao(database: MovieTvDatabase): MovieTvDao = database.movieTvDao()

    @Provides
    fun provideFavoriteMovieTvDao(database: MovieTvDatabase): MovieTvDaoFavorite = database.favoriteMovieTvDao()
}
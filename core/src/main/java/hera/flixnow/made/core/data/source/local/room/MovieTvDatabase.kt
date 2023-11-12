package hera.flixnow.made.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import hera.flixnow.made.core.data.source.local.entity.EntityFavoriteMovieTv
import hera.flixnow.made.core.data.source.local.entity.EntityMovieTv

@Database(
    entities = [EntityMovieTv::class, EntityFavoriteMovieTv::class],
    version = 1,
    exportSchema = false
)
abstract class MovieTvDatabase : RoomDatabase() {
    abstract fun movieTvDao(): MovieTvDao
    abstract fun favoriteMovieTvDao(): MovieTvDaoFavorite
}
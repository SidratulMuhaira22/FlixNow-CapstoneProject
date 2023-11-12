package hera.flixnow.made.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import hera.flixnow.made.core.data.source.local.entity.EntityFavoriteMovieTv

@Dao
interface MovieTvDaoFavorite {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: EntityFavoriteMovieTv)

    @Delete
    fun delete(data: EntityFavoriteMovieTv)

    //Movie item has title
    @Query("select * from favorite_movie_tv where title != ''")
    fun getFavoriteMovies(): DataSource.Factory<Int, EntityFavoriteMovieTv>

    //Tv show item has title
    @Query("select * from favorite_movie_tv where name != ''")
    fun getFavoriteTvShows(): DataSource.Factory<Int, EntityFavoriteMovieTv>

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_movie_tv WHERE id = :id)")
    suspend fun isExist(id: Int): Boolean
}
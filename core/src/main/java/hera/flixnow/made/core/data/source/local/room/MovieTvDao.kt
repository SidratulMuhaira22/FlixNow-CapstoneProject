package hera.flixnow.made.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hera.flixnow.made.core.data.source.local.entity.EntityMovieTv
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieTvDao {
    //Movie item has title
    @Query("select * from movie_tv where title != ''")
    fun getMovies(): Flow<List<EntityMovieTv>>

    //Tv show item has name
    @Query("select * from movie_tv where name != ''")
    fun getTvShows(): Flow<List<EntityMovieTv>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieTv(movie: List<EntityMovieTv>)
}
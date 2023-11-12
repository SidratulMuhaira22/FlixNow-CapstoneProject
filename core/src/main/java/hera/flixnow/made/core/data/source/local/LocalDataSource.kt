package hera.flixnow.made.core.data.source.local

import androidx.paging.DataSource
import hera.flixnow.made.core.data.source.local.entity.EntityFavoriteMovieTv
import hera.flixnow.made.core.data.source.local.entity.EntityMovieTv
import hera.flixnow.made.core.data.source.local.room.MovieTvDaoFavorite
import hera.flixnow.made.core.data.source.local.room.MovieTvDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieTvDao: MovieTvDao, private val movieTvDaoFavorite: MovieTvDaoFavorite) {

    fun getMovieList(): Flow<List<EntityMovieTv>> = movieTvDao.getMovies()
    fun getTvShowList(): Flow<List<EntityMovieTv>> = movieTvDao.getTvShows()
    suspend fun insertMovieTv(movie: List<EntityMovieTv>) = movieTvDao.insertMovieTv(movie)
    fun setFavoriteMovieTv(movie: EntityFavoriteMovieTv, saved: Boolean) {
        if (!saved) movieTvDaoFavorite.insert(movie) else movieTvDaoFavorite.delete(movie)
    }
    fun getFavoriteMovies(): DataSource.Factory<Int, EntityFavoriteMovieTv> = movieTvDaoFavorite.getFavoriteMovies()
    fun getFavoriteTvShows(): DataSource.Factory<Int, EntityFavoriteMovieTv> = movieTvDaoFavorite.getFavoriteTvShows()
    suspend fun isFavorite(movieTv: EntityFavoriteMovieTv): Boolean = movieTvDaoFavorite.isExist(movieTv.id)
}
package hera.flixnow.made.core.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import hera.flixnow.made.core.data.NetworkBoundResource
import hera.flixnow.made.core.data.Resource
import hera.flixnow.made.core.data.source.local.LocalDataSource
import hera.flixnow.made.core.data.source.remote.RemoteDataSource
import hera.flixnow.made.core.data.source.remote.network.ApiResponse
import hera.flixnow.made.core.data.source.remote.response.ResponseMovieTv
import hera.flixnow.made.core.domain.model.MovieTvModel
import hera.flixnow.made.core.domain.repository.RepositoryIMovieTv
import hera.flixnow.made.core.utils.ExecutorsApp
import hera.flixnow.made.core.utils.DataMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryTMDBIMovieTv @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val executorsApp: ExecutorsApp
) : RepositoryIMovieTv {

    override fun getMovies(): Flow<Resource<List<MovieTvModel>>> =
        object : NetworkBoundResource<List<MovieTvModel>, List<ResponseMovieTv>>() {
            override fun loadFromDB(): Flow<List<MovieTvModel>> {
                return localDataSource.getMovieList().map {
                    DataMap.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieTvModel>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseMovieTv>>> =
                remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<ResponseMovieTv>) {
                val tourismList = DataMap.mapResponseToEntities(data)
                localDataSource.insertMovieTv(tourismList)
            }
        }.asFlow()

    override fun getTvShows(): Flow<Resource<List<MovieTvModel>>> =
        object : NetworkBoundResource<List<MovieTvModel>, List<ResponseMovieTv>>() {
            override fun loadFromDB(): Flow<List<MovieTvModel>> {
                return localDataSource.getTvShowList().map {
                    DataMap.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieTvModel>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseMovieTv>>> =
                remoteDataSource.getTvShows()

            override suspend fun saveCallResult(data: List<ResponseMovieTv>) {
                val tourismList = DataMap.mapResponseToEntities(data)
                localDataSource.insertMovieTv(tourismList)
            }
        }.asFlow()

    override fun getFavoriteMovies(): LiveData<PagedList<MovieTvModel>> {
        val favMovies = localDataSource.getFavoriteMovies().map {
            DataMap.mapFavoriteEntityToDomain(it)
        }

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(favMovies, config).build()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<MovieTvModel>> {
        val favTvShows = localDataSource.getFavoriteTvShows().map {
            DataMap.mapFavoriteEntityToDomain(it)
        }

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(favTvShows, config).build()
    }

    override fun searchMovies(query: String): Flow<Resource<List<MovieTvModel>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.searchMovies(query).first()) {
            is ApiResponse.Success -> {
                val response = DataMap.mapResponseToEntities(apiResponse.data)
                emit(Resource.Success(DataMap.mapEntitiesToDomain(response)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error<List<MovieTvModel>>("No result found, please try different keyword"))
            }
            is ApiResponse.Error -> {
                emit(
                    Resource.Error<List<MovieTvModel>>(
                        apiResponse.errorMessage
                    )
                )
            }
        }
    }

    override fun searchTvShows(query: String): Flow<Resource<List<MovieTvModel>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.searchTvShows(query).first()) {
            is ApiResponse.Success -> {
                val response = DataMap.mapResponseToEntities(apiResponse.data)
                emit(Resource.Success(DataMap.mapEntitiesToDomain(response)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error<List<MovieTvModel>>("No result found, please try different keyword"))
            }
            is ApiResponse.Error -> {
                emit(
                    Resource.Error<List<MovieTvModel>>(
                        apiResponse.errorMessage
                    )
                )
            }
        }
    }

    override fun setFavoriteMovieTv(movieTvModel: MovieTvModel, saved: Boolean) {
        val movieTvEntity = DataMap.mapDomainToFavoriteEntity(movieTvModel)
        executorsApp.diskIO().execute { localDataSource.setFavoriteMovieTv(movieTvEntity, saved) }
    }

    override fun isFavorite(movieTvModel: MovieTvModel): Flow<Boolean> {
        val movieTvEntity = DataMap.mapDomainToFavoriteEntity(movieTvModel)
        return flow {
            emit(localDataSource.isFavorite(movieTvEntity))
        }
    }
}


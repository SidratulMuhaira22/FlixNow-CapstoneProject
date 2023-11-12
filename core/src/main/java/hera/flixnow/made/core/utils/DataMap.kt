package hera.flixnow.made.core.utils

import hera.flixnow.made.core.data.source.local.entity.EntityFavoriteMovieTv
import hera.flixnow.made.core.data.source.local.entity.EntityMovieTv
import hera.flixnow.made.core.data.source.remote.response.ResponseMovieTv
import hera.flixnow.made.core.domain.model.MovieTvModel

object DataMap {

    fun mapResponseToEntities(input: List<ResponseMovieTv>): List<EntityMovieTv> {
        val movieList = ArrayList<EntityMovieTv>()
        input.map {
            val movie = EntityMovieTv(
                id = it.id ?: 0,
                title = it.title,
                name = it.name,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                backdrop_path = it.backdrop_path,
                vote_average = it.vote_average,
                release_date = it.release_date,
                first_air_date = it.first_air_date
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<EntityMovieTv>): List<MovieTvModel> =
        input.map {
            MovieTvModel(
                id = it.id,
                title = it.title,
                name = it.name,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                backdrop_path = it.backdrop_path,
                vote_average = it.vote_average,
                release_date = it.release_date,
                first_air_date = it.first_air_date
            )
        }

    fun mapFavoriteEntityToDomain(input: EntityFavoriteMovieTv) = MovieTvModel(
            id = input.id,
            title = input.title,
            name = input.name,
            overview = input.overview,
            popularity = input.popularity,
            poster_path = input.poster_path,
            backdrop_path = input.backdrop_path,
            vote_average = input.vote_average,
            release_date = input.release_date,
            first_air_date = input.first_air_date
    )

    fun mapDomainToFavoriteEntity(input: MovieTvModel) = EntityFavoriteMovieTv(
            id = input.id,
            title = input.title,
            name = input.name,
            overview = input.overview,
            popularity = input.popularity,
            poster_path = input.poster_path,
            backdrop_path = input.backdrop_path,
            vote_average = input.vote_average,
            release_date = input.release_date,
            first_air_date = input.first_air_date
    )
}
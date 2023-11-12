package hera.flixnow.made.core.di

import dagger.Binds
import dagger.Module
import hera.flixnow.made.core.data.source.RepositoryTMDBIMovieTv
import hera.flixnow.made.core.domain.repository.RepositoryIMovieTv

@Module(includes = [ModuleNetwork::class, ModuleDatabase::class])
abstract class ModuleRepository {

  @Suppress("unused")
  @Binds
  abstract fun provideRepository(repositoryTMDB: RepositoryTMDBIMovieTv): RepositoryIMovieTv
}
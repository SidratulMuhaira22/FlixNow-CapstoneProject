package hera.flixnow.made.core.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import hera.flixnow.made.core.domain.repository.RepositoryIMovieTv
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ModuleRepository::class]
)
interface ComponentCore {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ComponentCore
    }

    fun provideRepository() : RepositoryIMovieTv
}
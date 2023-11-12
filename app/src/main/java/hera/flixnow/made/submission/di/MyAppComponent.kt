package hera.flixnow.made.submission.di

import dagger.Component
import hera.flixnow.made.submission.ui.components.MainActivityBase
import hera.flixnow.made.submission.ui.components.detail.ActivityDetailBase
import hera.flixnow.made.submission.ui.components.movie.MovieBaseFragment
import hera.flixnow.made.submission.ui.components.tv.TvBaseFragment
import hera.flixnow.made.core.di.ComponentCore
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@MyAppScope
@Component(
    dependencies = [ComponentCore::class],
    modules = [MyAppModule::class, MyViewModelModule::class]
)
interface MyAppComponent {
    @Component.Factory
    interface Factory {
        fun create(componentCore: ComponentCore): MyAppComponent
    }

    fun inject(activity: MainActivityBase)
    fun inject(activity: ActivityDetailBase)
    fun inject(fragment: MovieBaseFragment)
    fun inject(fragment: TvBaseFragment)
}
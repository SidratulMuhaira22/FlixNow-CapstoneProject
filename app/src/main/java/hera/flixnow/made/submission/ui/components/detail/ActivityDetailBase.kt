package hera.flixnow.made.submission.ui.components.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import hera.flixnow.made.submission.MyApplication
import hera.flixnow.made.submission.databinding.ActivityDetailBinding
import hera.flixnow.made.submission.ui.FactoryViewModel
import hera.flixnow.made.core.R
import hera.flixnow.made.core.domain.model.MovieTvModel
import hera.flixnow.made.core.ui.base.ActivityBase
import hera.flixnow.made.core.utils.ext.observe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class ActivityDetailBase : ActivityBase<ActivityDetailBinding>({ ActivityDetailBinding.inflate(it) }) {

    @Inject
    lateinit var factory: FactoryViewModel

    private val viewModel: ViewModelDetail by viewModels { factory }

    @ExperimentalCoroutinesApi
    override fun ActivityDetailBinding.onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).myAppComponent.inject(this@ActivityDetailBase)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        val movieTvModel = intent.getParcelableExtra<MovieTvModel>(EXTRA_MOVIE_TV)
        movieTvModel?.let {
            viewModel.setSelectedItem(it)
        }
    }

    override fun observeViewModel() {
        observe(viewModel.movieTvModelItem) { binding.item = it }
        observe(viewModel.isFavorite, ::setFavoriteState)
    }

    private fun setFavoriteState(isFavorite: Boolean) {
        binding.fabFav.setOnClickListener {
            viewModel.setToFavorite(isFavorite)
        }
        binding.fabFav.setImageDrawable(
                ContextCompat.getDrawable(
                        this@ActivityDetailBase,
                        if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
                )
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val EXTRA_MOVIE_TV = "key.EXTRA_MOVIE_TV"

        fun navigate(activity: Activity, movieTvModel: MovieTvModel) {
            Intent(activity, ActivityDetailBase::class.java).apply {
                putExtra(EXTRA_MOVIE_TV, movieTvModel)
            }.also {
                activity.startActivity(it)
            }
        }
    }
}
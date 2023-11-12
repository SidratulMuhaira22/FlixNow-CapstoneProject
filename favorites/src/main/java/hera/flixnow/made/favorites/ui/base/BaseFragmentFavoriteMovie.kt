package hera.flixnow.made.favorites.ui.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import com.google.android.material.snackbar.Snackbar
import hera.flixnow.made.submission.R
import hera.flixnow.made.submission.ui.FactoryViewModel
import hera.flixnow.made.core.di.ComponentCore
import hera.flixnow.made.core.di.DaggerComponentCore
import hera.flixnow.made.core.domain.model.MovieTvModel
import hera.flixnow.made.core.ui.base.BaseFragment
import hera.flixnow.made.core.utils.ext.gone
import hera.flixnow.made.core.utils.ext.observe
import hera.flixnow.made.core.utils.ext.shareMovieTv
import hera.flixnow.made.core.utils.ext.visible
import hera.flixnow.made.favorites.databinding.FragmentFavoriteMovieBinding
import hera.flixnow.made.favorites.di.DaggerComponentFavorite
import hera.flixnow.made.favorites.ui.ViewModelFavorite
import hera.flixnow.made.submission.ui.components.detail.ActivityDetailBase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class BaseFragmentFavoriteMovie : BaseFragment<FragmentFavoriteMovieBinding>({ FragmentFavoriteMovieBinding.inflate(it) }) {

    @Inject
    lateinit var factory: FactoryViewModel

    private val componentCore: ComponentCore by lazy {
        DaggerComponentCore.factory().create(requireActivity())
    }
    private val viewModel: ViewModelFavorite by viewModels { factory }
    private val adapter by lazy { BaseFavoriteMovieAdapter() }

    override fun FragmentFavoriteMovieBinding.onViewCreated(savedInstanceState: Bundle?) {
        binding?.rvFavoriteMovie?.adapter = this@BaseFragmentFavoriteMovie.adapter
        adapter.lifecycleOwner = this@BaseFragmentFavoriteMovie
        adapter.viewModel = this@BaseFragmentFavoriteMovie.viewModel
        adapter.listener = { _, _, item ->
            ActivityDetailBase.navigate(requireActivity(), item)
        }
        adapter.favoriteListener = { item, isFavorite ->
            viewModel.setToFavorite(item, isFavorite)
            binding?.apply {
                Snackbar.make(root, getString(R.string.deleted_favorite, getString(R.string.movie)), Snackbar.LENGTH_LONG).apply {
                    setAction(getString(R.string.undo)) {
                        viewModel.setToFavorite(item, false)
                    }
                    show()
                }
            }
        }
        adapter.shareListener = { requireActivity().shareMovieTv(it) }
    }

    override fun observeViewModel() {
        observe(viewModel.favoriteMovies, ::handleFavMovies)
    }

    private fun handleFavMovies(favMovies: PagedList<MovieTvModel>) {
        if (!favMovies.isNullOrEmpty()) {
            binding?.emptyFavorite?.root?.gone()
            binding?.rvFavoriteMovie?.visible()
            adapter.submitList(favMovies)
        } else {
            binding?.emptyFavorite?.root?.visible()
            binding?.rvFavoriteMovie?.gone()
        }
    }

    @ExperimentalCoroutinesApi
    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerComponentFavorite.builder().componentCore(componentCore).build().inject(this)
    }
}
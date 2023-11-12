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
import hera.flixnow.made.favorites.databinding.FragmentFavoriteTvBinding
import hera.flixnow.made.favorites.di.DaggerComponentFavorite
import hera.flixnow.made.favorites.ui.ViewModelFavorite
import hera.flixnow.made.submission.ui.components.detail.ActivityDetailBase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class BaseFragmentFavoriteTv : BaseFragment<FragmentFavoriteTvBinding>({ FragmentFavoriteTvBinding.inflate(it) }) {

    @Inject
    lateinit var factory: FactoryViewModel

    private val componentCore: ComponentCore by lazy {
        DaggerComponentCore.factory().create(requireActivity())
    }
    private val viewModel: ViewModelFavorite by viewModels { factory }
    private val adapter by lazy { BaseFavoriteMovieAdapter() }

    override fun FragmentFavoriteTvBinding.onViewCreated(savedInstanceState: Bundle?) {
        binding?.rvFavoriteTv?.adapter = this@BaseFragmentFavoriteTv.adapter
        adapter.lifecycleOwner = this@BaseFragmentFavoriteTv
        adapter.viewModel = this@BaseFragmentFavoriteTv.viewModel
        adapter.listener = { _, _, item ->
            ActivityDetailBase.navigate(requireActivity(), item)
        }
        adapter.favoriteListener = { item, isFavorite ->
            viewModel.setToFavorite(item, isFavorite)
            binding?.apply {
                Snackbar.make(root, getString(R.string.deleted_favorite, getString(R.string.tv_show)), Snackbar.LENGTH_LONG).apply {
                    setAction(getString(R.string.undo)) {
                        viewModel.setToFavorite(item, false)
                        return@setAction
                    }
                    show()
                }
            }
        }
        adapter.shareListener = { requireActivity().shareMovieTv(it) }
    }

    override fun observeViewModel() {
        observe(viewModel.favoriteTvShows, ::handleFavTvShows)
    }

    private fun handleFavTvShows(favMovies: PagedList<MovieTvModel>) {
        if (!favMovies.isNullOrEmpty()) {
            binding?.emptyFavorite?.root?.gone()
            binding?.rvFavoriteTv?.visible()
            adapter.submitList(favMovies)
        } else {
            binding?.emptyFavorite?.root?.visible()
            binding?.rvFavoriteTv?.gone()
        }
    }

    @ExperimentalCoroutinesApi
    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerComponentFavorite.builder().componentCore(componentCore).build().inject(this)
    }
}
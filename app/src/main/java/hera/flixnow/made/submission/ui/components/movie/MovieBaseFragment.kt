package hera.flixnow.made.submission.ui.components.movie

import android.content.Context
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import hera.flixnow.made.submission.MyApplication
import hera.flixnow.made.submission.R
import hera.flixnow.made.submission.databinding.FragmentMovieBinding
import hera.flixnow.made.submission.ui.FactoryViewModel
import hera.flixnow.made.submission.ui.components.detail.ActivityDetailBase
import hera.flixnow.made.core.data.Resource
import hera.flixnow.made.core.domain.model.MovieTvModel
import hera.flixnow.made.core.ui.base.BaseFragment
import hera.flixnow.made.core.utils.ext.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieBaseFragment : BaseFragment<FragmentMovieBinding>({ FragmentMovieBinding.inflate(it) }) {

    @Inject
    lateinit var factory: FactoryViewModel

    private val viewModel: ViewModelMovie by viewModels { factory }
    private val adapter by lazy { AdapterMovieBase() }

    override fun FragmentMovieBinding.onViewCreated(savedInstanceState: Bundle?) {
        binding?.apply {
            rvMovie.adapter = this@MovieBaseFragment.adapter
            rvMovie.hasFixedSize()
            rvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    appbar.isSelected = recyclerView.canScrollVertically(-1)
                }
            })
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    lifecycleScope.launch {
                        viewModel.queryChannel.send(p0.toString())
                    }
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    lifecycleScope.launch {
                        viewModel.queryChannel.send(p0.toString())
                    }
                    return true
                }
            })
        }

        adapter.lifecycleOwner = this@MovieBaseFragment
        adapter.viewModel = this@MovieBaseFragment.viewModel
        adapter.listener = { _, _, item ->
            ActivityDetailBase.navigate(requireActivity(), item)
        }
        adapter.favoriteListener = { item, isFavorite ->
            viewModel.setToFavorite(item, isFavorite)
        }
        adapter.shareListener = { requireActivity().shareMovieTv(it) }
    }

    override fun observeViewModel() {
        observe(viewModel.movies, ::handleMovies)
        observe(viewModel.search) { searchResult ->
            observe(searchResult?.asLiveData(), ::handleSearch)
        }
    }

    private fun handleMovies(movies: Resource<List<MovieTvModel>>) {
        binding?.apply {
            when (movies) {
                is Resource.Loading -> {
                    errorLayout.gone()
                    loading.root.visible()
                }
                is Resource.Success -> {
                    loading.root.gone()
                    errorLayout.gone()
                    adapter.submitList(movies.data)
                }
                is Resource.Error -> {
                    loading.root.gone()
                    if (movies.data.isNullOrEmpty()) {
                        errorLayout.visible()
                        error.message.text =
                            movies.message ?: getString(R.string.default_error_message)
                    } else {
                        requireContext().showToast(getString(R.string.default_error_message))
                        adapter.submitList(movies.data)
                    }
                }
            }
        }
    }

    private fun handleSearch(movies: Resource<List<MovieTvModel>>) {
        binding?.apply {
            when (movies) {
                is Resource.Loading -> {
                    errorLayout.gone()
                    loading.root.visible()
                }
                is Resource.Success -> {
                    loading.root.gone()
                    errorLayout.gone()
                    adapter.submitList(movies.data)
                }
                is Resource.Error -> {
                    loading.root.gone()
                    if (movies.data.isNullOrEmpty()) {
                        errorLayout.visible()
                        error.message.text =
                            movies.message ?: getString(R.string.default_error_message)
                    } else {
                        requireContext().showToast(getString(R.string.default_error_message))
                        adapter.submitList(movies.data)
                    }
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).myAppComponent.inject(this)
    }
}
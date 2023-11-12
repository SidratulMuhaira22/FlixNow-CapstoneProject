package hera.flixnow.made.submission.ui.components.tv

import android.content.Context
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import hera.flixnow.made.submission.MyApplication
import hera.flixnow.made.submission.R
import hera.flixnow.made.submission.databinding.FragmentTvBinding
import hera.flixnow.made.submission.ui.ViewModelFactory
import hera.flixnow.made.submission.ui.components.detail.ActivityDetailBase
import hera.flixnow.made.core.data.Resource
import hera.flixnow.made.core.domain.model.MovieTvModel
import hera.flixnow.made.core.ui.base.BaseFragment
import hera.flixnow.made.core.utils.ext.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvBaseFragment : BaseFragment<FragmentTvBinding>({ FragmentTvBinding.inflate(it) }) {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: ViewModelTv by viewModels { factory }
    private val adapter by lazy { AdapterTvBase() }

    override fun FragmentTvBinding.onViewCreated(savedInstanceState: Bundle?) {
        binding?.apply {
            rvTv.adapter = this@TvBaseFragment.adapter
            rvTv.hasFixedSize()
            rvTv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

        adapter.lifecycleOwner = this@TvBaseFragment
        adapter.viewModel = this@TvBaseFragment.viewModel
        adapter.listener = { _, _, item ->
            ActivityDetailBase.navigate(requireActivity(), item)
        }
        adapter.favoriteListener = { item, isFavorite ->
            viewModel.setToFavorite(item, isFavorite)
        }
        adapter.shareListener = { requireActivity().shareMovieTv(it) }
    }

    override fun observeViewModel() {
        observe(viewModel.tvShows, ::handleTvShows)
        observe(viewModel.search) { searchResult ->
            observe(searchResult?.asLiveData(), ::handleSearch)
        }
    }

    private fun handleTvShows(tvShows: Resource<List<MovieTvModel>>) {
        binding?.apply {
            when (tvShows) {
                is Resource.Loading -> {
                    errorLayout.gone()
                    loading.root.visible()
                }
                is Resource.Success -> {
                    loading.root.gone()
                    errorLayout.gone()
                    adapter.submitList(tvShows.data)
                }
                is Resource.Error -> {
                    loading.root.gone()
                    if (tvShows.data.isNullOrEmpty()) {
                        errorLayout.visible()
                        error.message.text =
                            tvShows.message ?: getString(R.string.default_error_message)
                    } else {
                        requireContext().showToast(getString(R.string.default_error_message))
                        adapter.submitList(tvShows.data)
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
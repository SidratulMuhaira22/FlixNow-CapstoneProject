package hera.flixnow.made.favorites.ui.base

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import hera.flixnow.made.submission.R
import hera.flixnow.made.core.databinding.ItemMovieTvBinding
import hera.flixnow.made.core.domain.model.MovieTvModel
import hera.flixnow.made.core.ui.base.PagedListAdapterBase
import hera.flixnow.made.core.utils.ext.observe
import hera.flixnow.made.favorites.ui.ViewModelFavorite

class  BaseFavoriteMovieAdapter : PagedListAdapterBase<MovieTvModel, ItemMovieTvBinding>(
    DIFF_CALLBACK
) {

    lateinit var viewModel: ViewModelFavorite
    lateinit var lifecycleOwner: LifecycleOwner

    var favoriteListener: ((item: MovieTvModel, isFavorite: Boolean) -> Unit)? = null
    var shareListener: ((item: MovieTvModel) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_movie_tv

    override fun onBindViewHolder(holder: PagedListAdapterBase.Companion.BaseViewHolder<ItemMovieTvBinding>, position: Int) {
        val item = getItem(position) ?: return
        holder.apply {
            binding.root.setOnClickListener { listener?.invoke(it, position, item) }
            lifecycleOwner.observe(viewModel.isFavorite(item)) { isFavorite ->
                binding.cbIsFav.setOnClickListener {
                    favoriteListener?.invoke(item, isFavorite)
                }
                binding.apply {
                    setVariable(BR.isFavorite, isFavorite)
                    executePendingBindings()
                }
            }
            binding.btnShare.setOnClickListener { shareListener?.invoke(item) }
            binding.apply {
                setVariable(BR.item, item)
                executePendingBindings()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieTvModel>() {
            override fun areContentsTheSame(oldItem: MovieTvModel, newItem: MovieTvModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: MovieTvModel, newItem: MovieTvModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
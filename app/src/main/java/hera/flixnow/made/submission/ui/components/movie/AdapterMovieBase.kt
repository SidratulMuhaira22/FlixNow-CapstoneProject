package hera.flixnow.made.submission.ui.components.movie

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import hera.flixnow.made.core.databinding.ItemMovieTvBinding
import hera.flixnow.made.core.domain.model.MovieTvModel
import hera.flixnow.made.core.ui.AdapterBase
import hera.flixnow.made.core.utils.ext.observe
import hera.flixnow.made.submission.R

class  AdapterMovieBase : AdapterBase<MovieTvModel, ItemMovieTvBinding>(DIFF_CALLBACK) {

    lateinit var viewModel: ViewModelMovie
    lateinit var lifecycleOwner: LifecycleOwner

    var favoriteListener: ((item: MovieTvModel, isFavorite: Boolean) -> Unit)? = null
    var shareListener: ((item: MovieTvModel) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_movie_tv

    override fun onBindViewHolder(holder: AdapterBase.Companion.BaseViewHolder<ItemMovieTvBinding>, position: Int) {
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
package hera.flixnow.made.favorites.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import hera.flixnow.made.submission.R
import hera.flixnow.made.core.di.ComponentCore
import hera.flixnow.made.core.di.DaggerComponentCore
import hera.flixnow.made.core.ui.base.BaseFragment
import hera.flixnow.made.favorites.databinding.FragmentFavoriteBinding
import hera.flixnow.made.favorites.di.DaggerComponentFavorite
import hera.flixnow.made.favorites.ui.base.BaseFragmentFavoriteMovie
import hera.flixnow.made.favorites.ui.base.BaseFragmentFavoriteTv
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.math.abs

class FragmentFavorite : BaseFragment<FragmentFavoriteBinding>({ FragmentFavoriteBinding.inflate(it) }) {

    private val componentCore: ComponentCore by lazy {
        DaggerComponentCore.factory().create(requireActivity())
    }

    override fun FragmentFavoriteBinding.onViewCreated(savedInstanceState: Bundle?) {
        binding?.appbar?.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding?.appbar?.isSelected = abs(verticalOffset) - appBarLayout.totalScrollRange == 0
        })
        initFavoritePagerAdapter()
        initTabLayout()
    }

    override fun observeViewModel() {}

    private fun initFavoritePagerAdapter() {
        val pagerAdapter = FavoritePagerAdapter(childFragmentManager, lifecycle)
        binding?.apply {
            viewPager.apply {
                offscreenPageLimit = 2
                adapter = pagerAdapter
            }
        }
    }

    private fun initTabLayout() {
        binding?.apply {
            TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
                tab.text = when (pos) {
                    0 -> getString(R.string.movie)
                    else -> getString(R.string.tv_show)
                }
            }.attach()
        }
    }

    private class FavoritePagerAdapter(
            fm: FragmentManager,
            lifecycle: Lifecycle
    ) : FragmentStateAdapter(fm, lifecycle) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> BaseFragmentFavoriteMovie()
            else -> BaseFragmentFavoriteTv()
        }
    }

    @ExperimentalCoroutinesApi
    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerComponentFavorite.builder().componentCore(componentCore).build().inject(this)
    }
}
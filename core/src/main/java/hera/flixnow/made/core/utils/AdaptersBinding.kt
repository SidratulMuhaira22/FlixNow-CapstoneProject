package hera.flixnow.made.core.utils

import android.widget.CheckBox
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import hera.flixnow.made.core.BuildConfig
import hera.flixnow.made.core.R

@BindingAdapter("app:imageUrl")
fun setImageUrl(imageView: ImageView, imageUrl: String?) {
  imageUrl?.let {
    Glide.with(imageView.context)
            .load("${BuildConfig.IMG_URL}$imageUrl")
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_error)
            .into(imageView)
  }
}

@BindingAdapter("app:isFavorite")
fun setIsFavorite(checkBox: CheckBox, isFavorite: Boolean) {
  checkBox.isChecked = isFavorite
}
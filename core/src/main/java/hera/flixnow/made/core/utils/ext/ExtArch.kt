package hera.flixnow.made.core.utils.ext

import android.content.Context
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import hera.flixnow.made.core.domain.model.MovieTvModel

fun <T> LifecycleOwner.observe(liveData: LiveData<T>?, action: (t: T) -> Unit) {
    liveData?.observe(this,
        { it?.let { t -> action(t) } }
    )
}

fun Context.showToast(msg: String?) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun FragmentActivity.shareMovieTv(movieTvModel: MovieTvModel) {
    val mimeType = "text/plain"
    ShareCompat.IntentBuilder.from(this).apply {
        setType(mimeType)
        setChooserTitle(movieTvModel.title)
        setText("https://www.themoviedb.org/movie/${movieTvModel.id}")
        startChooser()
    }
}
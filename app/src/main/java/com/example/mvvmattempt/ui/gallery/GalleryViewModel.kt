package com.example.mvvmattempt.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmattempt.R
import com.example.mvvmattempt.data.Picture
import com.example.mvvmattempt.data.source.repository.PictureRepository
import com.example.mvvmattempt.ui.common.LoaderViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class GalleryViewModel @ViewModelInject constructor(
    private val repository: PictureRepository
) : LoaderViewModel() {

    private val _picList = MutableLiveData<List<Picture>>()
    val picList: LiveData<List<Picture>> = _picList

    init {
        load(ALBUM_ID)
    }

    override fun load(id: Long) {
        disposables.add(
            repository.getPicturesByAlbumId(ALBUM_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingStarted() }
                .subscribe(
                    { pics ->
                        Timber.d("success getting ${pics.size} pics")
                        if (pics.isNotEmpty()) {  // Room returns empty list if
                            loadingStopped()      // there are no entries
                            _picList.value = pics
                        } else {
                            loadingFailed()
                        }
                    },
                    {
                        Timber.d("Failed getting pics")
                        loadingFailed()
                    }
                )
        )
    }

    override fun retry() {
        load(ALBUM_ID)
    }

    override fun loadingFailed() {
        loadingStopped()
        setMessageId(R.string.loading_error)
    }

    companion object {
        const val ALBUM_ID = 1L  // constant id for simplicity
    }
}
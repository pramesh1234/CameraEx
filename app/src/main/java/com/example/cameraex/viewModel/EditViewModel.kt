package com.example.cameraex.viewModel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cameraex.Filters
import com.example.cameraex.constants.CameraExConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditViewModel : ViewModel() {
    val getBitmapLiveData = MutableLiveData<Bitmap>()
    fun setFilter(bitmap: Bitmap, type: Int) {
        var filteredBitmap: Bitmap? = null
        CoroutineScope(Dispatchers.IO).launch {

            when (type) {
                CameraExConstants.N0_FILTER -> {
                    filteredBitmap = bitmap
                    getBitmapLiveData.postValue(filteredBitmap!!)

                }
                CameraExConstants.FILTER_1 -> {
                    filteredBitmap = Filters.setBlackAndWhiteFilter(bitmap)
                    getBitmapLiveData.postValue(filteredBitmap!!)

                }
                CameraExConstants.FILTER_2 -> {
                    filteredBitmap = Filters.setFilter2(bitmap)
                    getBitmapLiveData.postValue(filteredBitmap!!)

                }
                CameraExConstants.FILTER_3 -> {
                    filteredBitmap = Filters.setFilter3(bitmap)
                    getBitmapLiveData.postValue(filteredBitmap!!)

                }
                CameraExConstants.FILTER_4 -> {
                    filteredBitmap = Filters.setFilter4(bitmap)
                    getBitmapLiveData.postValue(filteredBitmap!!)
                }

            }
        }

    }
}
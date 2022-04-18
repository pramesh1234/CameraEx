package com.example.cameraex.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class BitmapUtils {
    companion object{
        @Throws(IOException::class)
        fun saveBitmapToCache(bitmap: Bitmap,applicationContext:Context) {
            val filename = "temp_image.jpg"
            val cacheFile = File(applicationContext.cacheDir, filename)
            val out: OutputStream = FileOutputStream(cacheFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        }

        fun getBitmapFromCache(applicationContext:Context): Bitmap {
            val cacheFile = File(applicationContext.cacheDir, "temp_image.jpg")
            return BitmapFactory.decodeFile(cacheFile.absolutePath)
        }
    }
}
package com.example.cameraex

import android.graphics.Bitmap
import android.graphics.Color

class Filters {
    companion object {
        fun setBlackAndWhiteFilter(bitmap: Bitmap): Bitmap {
            val copyOfBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
            val bitmapHeight = copyOfBitmap.height
            val bitmapWidth = copyOfBitmap.width
            for (i in 0 until bitmapWidth) {
                for (j in 0 until bitmapHeight) {
                    val oldPixel = bitmap.getPixel(i, j)
                    val red = Color.red(oldPixel)
                    val green = Color.green(oldPixel)
                    val blue = Color.blue(oldPixel)
                    val alpha = Color.alpha(oldPixel)
                    val intensity = (red + green + blue) / 3
                    val newRed = intensity
                    val newGreen = intensity
                    val newBlue = intensity
                    val newPixel = Color.argb(alpha, newRed, newGreen, newBlue)
                    copyOfBitmap.setPixel(i, j, newPixel)
                }
            }
            return copyOfBitmap
        }
        fun setFilter2(bitmap: Bitmap): Bitmap {
            val copyOfBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
            val bitmapHeight = copyOfBitmap.height
            val bitmapWidth = copyOfBitmap.width
            for (i in 0 until bitmapWidth) {
                for (j in 0 until bitmapHeight) {
                    val oldPixel = bitmap.getPixel(i, j)
                    val red = Color.red(oldPixel)
                    val green = Color.green(oldPixel)
                    val blue = Color.blue(oldPixel)
                    val alpha = Color.alpha(oldPixel)

                    val newRed = 0
                    val newGreen = 0
                    val newBlue = blue
                    val newPixel = Color.argb(alpha, newRed, newGreen, newBlue)
                    copyOfBitmap.setPixel(i, j, newPixel)
                }
            }
            return copyOfBitmap
        }
        fun setFilter3(bitmap: Bitmap): Bitmap {
            val copyOfBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
            val bitmapHeight = copyOfBitmap.height
            val bitmapWidth = copyOfBitmap.width
            for (i in 0 until bitmapWidth) {
                for (j in 0 until bitmapHeight) {
                    val oldPixel = bitmap.getPixel(i, j)
                    val red = Color.red(oldPixel)
                    val green = Color.green(oldPixel)
                    val blue = Color.blue(oldPixel)
                    val alpha = Color.alpha(oldPixel)
                    val newRed = 0
                    val newGreen = green
                    val newBlue = 0
                    val newPixel = Color.argb(alpha, newRed, newGreen, newBlue)
                    copyOfBitmap.setPixel(i, j, newPixel)
                }
            }
            return copyOfBitmap
        }
        fun setFilter4(bitmap: Bitmap): Bitmap {
            val copyOfBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
            val bitmapHeight = copyOfBitmap.height
            val bitmapWidth = copyOfBitmap.width
            for (i in 0 until bitmapWidth) {
                for (j in 0 until bitmapHeight) {
                    val oldPixel = bitmap.getPixel(i, j)
                    val red = Color.red(oldPixel)
                    val green = Color.green(oldPixel)
                    val blue = Color.blue(oldPixel)
                    val alpha = Color.alpha(oldPixel)
                    val newRed = red
                    val newGreen = 0
                    val newBlue = 0
                    val newPixel = Color.argb(alpha, newRed, newGreen, newBlue)
                    copyOfBitmap.setPixel(i, j, newPixel)
                }
            }
            return copyOfBitmap
        }
    }

}
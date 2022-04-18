package com.example.cameraex.views

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cameraex.CropImageView
import com.example.cameraex.R
import com.example.cameraex.databinding.ActivityCropBinding
import com.example.cameraex.utils.BitmapUtils

class CropActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCropBinding
    lateinit var bitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCropBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        bitmap = BitmapUtils.getBitmapFromCache(applicationContext)
        val cropImageView : CropImageView.CropImageView = findViewById(R.id.cropIV)
        cropImageView.setImageBitmap(bitmap)

        binding.saveBtn.setOnClickListener {
            bitmap= cropImageView.croppedImage!!
            BitmapUtils.saveBitmapToCache(bitmap,applicationContext)
            finish()
        }
    }
}
package com.example.cameraex.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.cameraex.databinding.ActivityMainBinding
import com.example.cameraex.utils.BitmapUtils


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var data: Bitmap? = null
    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                binding.editTV.visibility = View.VISIBLE
                binding.imageIV.visibility = View.VISIBLE
                binding.defaultMsgTV.visibility = View.INVISIBLE
                binding.imageIV.setImageBitmap(it.data?.extras?.get("data") as Bitmap)
                data = it.data?.extras?.get("data") as Bitmap
                BitmapUtils.saveBitmapToCache(data!!, applicationContext)

            }
        }
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                binding.imageIV.visibility = View.VISIBLE
                binding.editTV.visibility = View.VISIBLE
                binding.defaultMsgTV.visibility = View.INVISIBLE
                val bitmapData = getBitmapFromUri(uri)
                binding.imageIV.setImageBitmap(bitmapData)
                data = bitmapData
                BitmapUtils.saveBitmapToCache(data!!, applicationContext)
            }

        }
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                getResult.launch(i)
            } else {

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.takeSelfieIV.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                when {
                    ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        getResult.launch(i)
                    }
                    shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {


                    }
                    else -> {
                        requestPermissionLauncher.launch(
                            Manifest.permission.CAMERA
                        )
                    }
                }
            }
        }
        binding.openGalleryBtn.setOnClickListener {
            getContent.launch("image/*")
        }
        binding.editTV.setOnClickListener {
            val i = Intent(this, EditActivity::class.java)
            startActivity(i)
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()
        return image
    }


}
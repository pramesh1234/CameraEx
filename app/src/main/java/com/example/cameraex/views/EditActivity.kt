package com.example.cameraex.views

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cameraex.adapter.FilterAdapter
import com.example.cameraex.databinding.ActivityEditBinding
import com.example.cameraex.utils.BitmapUtils
import com.example.cameraex.viewModel.EditViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    lateinit var bitmap: Bitmap
    lateinit var originalBitmap: Bitmap
    var stack = Stack<Bitmap>()
    lateinit var adapter: FilterAdapter
    val viewModel by lazy {
        ViewModelProvider(this)[EditViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun initViews() {
        bitmap = BitmapUtils.getBitmapFromCache(applicationContext)
        originalBitmap = bitmap
        binding.imageIV.setImageBitmap(bitmap)
        binding.rotateIV.setOnClickListener {
            stack.push(bitmap)
            bitmap = rotateBitmap(bitmap)
            binding.imageIV.setImageBitmap(bitmap)
        }
        //Click listener for crop operations
        binding.cropIV.setOnClickListener {
            BitmapUtils.saveBitmapToCache(bitmap, applicationContext)
            val i = Intent(this@EditActivity, CropActivity::class.java)
            startActivity(i)
        }

        //click listener for saving the edited bitmap
        binding.saveTV.setOnClickListener {
            val job = CoroutineScope(Dispatchers.IO).launch {
                saveMediaToStorage(bitmap)
            }
            job.onJoin
            Toast.makeText(this, "Saved to photos", Toast.LENGTH_SHORT).show()


        }

        //Code for undo operations
        binding.undoIV.setOnClickListener {
            if (stack.size > 0) {
                bitmap = stack.pop()
                binding.imageIV.setImageBitmap(bitmap)
                if (stack.size == 0) {
                    stack.push(bitmap)
                }
            }
        }
        binding.filterRV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = FilterAdapter(this, ArrayList())
        binding.filterRV.adapter = adapter
        adapter.updateList(filterList())
        adapter.onItemClick = {
            viewModel.setFilter(originalBitmap, it)
        }
        viewModel.getBitmapLiveData.observe(this) {
            stack.push(bitmap)
            bitmap = it
            binding.imageIV.setImageBitmap(bitmap)
        }
    }

    private fun rotateBitmap(image: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.postScale(1f, 1f)
        matrix.postRotate(90f)
        return Bitmap.createBitmap(image, 0, 0, image.width, image.height, matrix, true)
    }

    private fun filterList(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("No Filter")
        list.add("B&W")
        list.add("Blue")
        list.add("Green")
        list.add("Red")
        return list
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun saveMediaToStorage(bitmap: Bitmap) {

        val filename = "${System.currentTimeMillis()}.jpg"


        var fos: OutputStream? = null


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            this.contentResolver?.also { resolver ->


                val contentValues = ContentValues().apply {


                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {

            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }

    override fun onResume() {
        super.onResume()
        stack.push(bitmap)
        originalBitmap = BitmapUtils.getBitmapFromCache(applicationContext)
        bitmap=originalBitmap
        binding.imageIV.setImageBitmap(bitmap)
    }
}
package com.example.carebout.view.community

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.carebout.R
import com.example.carebout.databinding.ActivityImageBinding
import com.example.carebout.databinding.FragmentOneBinding
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_image)

        val binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 갤러리 요청 런처
        val requesetGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            try {
                // inSampleSize 비율 계산, 지정
                val calRatio = calculateInSampleSize(
                    it.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.imgSize)
                )
                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio

                // 이미지 로딩
                var inputStream = contentResolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()
                inputStream!!.close()
                inputStream = null

                bitmap?.let {
                    binding.userImageView.setImageBitmap(bitmap)
                } ?: let {
                    Log.d("kkang", "bitmap null")
                }
            }catch (e: Exception) {
                Log.d("kkang", "bitmap null")
            }
        }

        binding.galleryButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requesetGalleryLauncher.launch(intent)
        }
        binding.cameraButton.setOnClickListener {
        }

        /*
    // 카메라 요청 런처
        val requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            // inSampleSize 비율 계산, 지정
            val calRatio = calculateInSampleSize(
                Uri.fromFile(File(filePath)),
                resources.getDimensionPixelSize(R.dimen.imgSize),
                resources.getDimensionPixelSize(R.dimen.imgSize)
            )
            val option = BitmapFactory.Options()
            option.inSampleSize = calRatio

            // 사진 파일 로딩
            val bitmap = BitmapFactory.decodeFile(filePath, option)
            bitmap?.let {
                binding.userImageView.setImageBitmap(bitmap)
            }
        }

        binding.cameraButton.setOnClickListener {
            //카메라 앱
            // 파일 준비
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
            filePath = file.absolutePath
            val photoUri = FileProvider.getUriForFile(this,
                "kr.ac.hallym.prac11_provider.fileprovider", file)

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            requestCameraFileLauncher.launch(intent)
        }
    }

         */

    }
    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true // 옵션만 설정하고자 true로 지정
        try {
            var inputStream = contentResolver.openInputStream(fileUri)
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= reqHeight &&
                halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}
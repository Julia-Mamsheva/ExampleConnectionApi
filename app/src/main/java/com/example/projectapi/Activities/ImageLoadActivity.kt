package com.example.projectapi.Activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import com.example.projectapi.databinding.ActivityImageLoadBinding


class ImageLoadActivity : AppCompatActivity() {
    lateinit var binding: ActivityImageLoadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageLoadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
            101
        );//запрашиваем разрешение на доступ к хранилищу

        listener()
    }

    private fun listener() {
        binding.profileImg.setOnClickListener()
        {
            checkPermission(
                Manifest.permission.READ_MEDIA_IMAGES,
                101
            )// При клике запускаем метод проверки доступа к хранилищу
        }
    }
    /**
     Метод для проверки доступа к хранилищу
     @param permission - вид прав
     @param requestCode - какой ответ должен быть получен (в нашем случае 101)
     */
    private fun checkPermission(permission: String, requestCode: Int) {

        if (ContextCompat.checkSelfPermission(this@ImageLoadActivity, permission) == PackageManager.PERMISSION_DENIED) {
            //Если разрешение отсутствует, то еще раз запрашиваем его
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_MEDIA_IMAGES),requestCode)
        } else {
            //Если разрешение есть, то запускаем галерею
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
        }
    }

    // Метод загрузки изображения в ImageView
    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == PackageManager.PERMISSION_DENIED) {
                val data = it.data //Получение данных
                val imgUri = data?.data // Идентифицируем изображение
                binding.profileImg.setImageURI(imgUri) //Загружаем в ImageView
            }
        }
}
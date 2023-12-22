package com.example.petrescuecapstone.ui

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.data.UiState
import com.example.petrescuecapstone.databinding.ActivityAddPetBinding
import com.example.petrescuecapstone.viewmodel.PredictViewModel
import com.example.petrescuecapstone.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddPetActivity : AppCompatActivity() {
    private val viewModel by viewModels<PredictViewModel> {
        ViewModelFactory.getInstance(this)
    }

    //foto
    var filePath: Uri? = null
    var data_foto: ByteArray? = null
    private val REQUEST_PICK_IMAGE = 1
    private val REQUEST_CODE = 13


    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

    lateinit var binding: ActivityAddPetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_pet)
        binding.lifecycleOwner = this
        val intent = intent
        val detail = intent.getStringExtra("detail")

        Log.d("surabaya", "onCreate: $detail")
        binding.btnGallery.setOnClickListener {
            pilihfile()
        }

        binding.btnCamera.setOnClickListener {
//            dispatchTakePictureIntent()
                    permission()

        }
        binding.btnUpload.setOnClickListener {
            if (data_foto != null) {
                val f: File = File(cacheDir, "image")
                f.createNewFile()

                val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), data_foto!!)
                val body = MultipartBody.Part.createFormData("image", f.name, reqFile)

                viewModel.predict(body).observe(this) { result ->
                    when (result) {
                        is UiState.Loading -> {
                            showLoading(true)
                        }

                        is UiState.Success -> {
                            val intent = Intent(this, InfoPetStatus::class.java)
                            intent.putExtra(
                                "image",
                                result.data.predImg.toString()
                            )
                            intent.putExtra("id_pet", result.data.petId)
                            intent.putExtra("detail", detail)
                            startActivity(intent)
                        }

                        is UiState.Error -> {
                            showLoading(false)
                            showToast(result.error)
                        }

                    }
                }

            }
        }

    }

    private var currentPhotoPath: String? = null

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Membuat nama file yang unik
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Simpan alamat file agar dapat diakses nanti
            currentPhotoPath = absolutePath
        }
    }
    val REQUEST_TAKE_PHOTO = 100
    // Fungsi untuk membuka kamera dan mengambil gambar
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Pastikan ada aplikasi kamera yang dapat menangani intent ini
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Buat file untuk menyimpan gambar yang diambil
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Handle error saat membuat file
                    ex.printStackTrace()
                    null
                }
                // Lanjutkan hanya jika file berhasil dibuat
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.petrescuecapstone.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun permission() {
        // Request camera permissions
        if (allPermissionsGranted()) {
            dispatchTakePictureIntent()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {

            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    var cameraUri: Uri? = null
    var selectedImageUri: Uri? = null

    private fun camera() {
        var values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "MyPicture")
        values.put(
            MediaStore.Images.Media.DESCRIPTION,
            "Photo taken on " + System.currentTimeMillis()
        )
        cameraUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

//this is used to open camera and get image file
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(cameraIntent, REQUEST_CODE)
    }


    private fun pilihfile() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_PICK_IMAGE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_IMAGE) {
                filePath = data?.data
                val compressedResizedBitmap = compressAndResizeImage(this, filePath!!)

                // Konversi Bitmap ke dalam bentuk byte array
                val byteArrayOutputStream = ByteArrayOutputStream()

                // Mengganti nilai kualitas kompresi ke 80 (atau sesuai kebutuhan)
                compressedResizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)

                data_foto = byteArrayOutputStream.toByteArray()

                // Konversi byte array kembali ke Bitmap
                val bitmap = BitmapFactory.decodeByteArray(data_foto, 0, data_foto!!.size)

                // Simpan Bitmap ke dalam file sementara
                val tempFile = File(cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
                tempFile.createNewFile()


                val fileOutputStream = FileOutputStream(tempFile)
                compressedResizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()

                // Menggunakan Picasso untuk menampilkan gambar
                Picasso.get()
                    .load(tempFile)
                    .into(binding.imageView) // Gantilah 'imageView' dengan ID ImageView yang digunakan

            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                // Gunakan path file hasil kamera untuk mengompres dan mengubah ukuran gambar
                currentPhotoPath?.let { photoPath ->
                    // Mendapatkan byte array hasil kompresi
                    val compressedByteArray = compressAndResizeCameraImage(photoPath)

                    // Simpan byte array ke dalam file sementara
                    val tempFile = File(cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
                    tempFile.writeBytes(compressedByteArray)

                    data_foto = compressedByteArray

                    // Gunakan Picasso untuk menampilkan gambar yang diambil dari kamera
                    Picasso.get()
                        .load(tempFile)
                        .into(binding.imageView)


                    // Sekarang Anda dapat menggunakan 'reqFile' untuk multipart.body
                }
                currentPhotoPath = null
            }


        }
    }

    private fun compressAndResizeCameraImage(filePath: String): ByteArray {
        val bitmap = BitmapFactory.decodeFile(filePath)
        val compressedBitmap = compressBitmap(bitmap)
        val resizedBitmap = resizeBitmap(compressedBitmap, 500, 500)

        // Konversi Bitmap ke dalam bentuk byte array
        val byteArrayOutputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream) // Sesuaikan kualitas kompresi sesuai kebutuhan
        return byteArrayOutputStream.toByteArray()
    }


    // Perbarui fungsi compressAndResizeImage agar mengembalikan bitmap yang telah diubah ukuran dan dikompres
    private fun compressAndResizeImage(context: Context, uri: Uri): Bitmap {
        val bitmap = decodeUriToBitmap(context, uri)
        val compressedBitmap = compressBitmap(bitmap)
        return resizeBitmap(compressedBitmap, 500, 500)
    }


    private fun decodeUriToBitmap(context: Context, uri: Uri): Bitmap {
        return BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
    }


    private fun compressBitmap(bitmap: Bitmap): Bitmap {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
        val compressedBitmap =
            BitmapFactory.decodeStream(byteArrayOutputStream.toByteArray().inputStream())
        byteArrayOutputStream.close()
        return compressedBitmap
    }

    private fun resizeBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }

    private fun saveBitmapToFile(context: Context, bitmap: Bitmap) {
        val file = File(context.cacheDir, "compressed_image.jpg")
        try {
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()

            // Now you can use the 'file' for further processing or upload
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
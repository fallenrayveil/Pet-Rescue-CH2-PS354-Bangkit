package com.example.petrescuecapstone.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.databinding.ActivityInfoPetStatusBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class InfoPetStatus : AppCompatActivity() {
    lateinit var binding: ActivityInfoPetStatusBinding
    var type_pet: String? = null
    var gender_Pet: String? = null
    var tanggal: String? = null
    companion object{
        var view: Activity? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info_pet_status)
        binding.lifecycleOwner = this
        view = this
        val intent = intent
        val image = intent.getStringExtra("image")
        val detail = intent.getStringExtra("detail")
        val id_pet = intent.getIntExtra("id_pet",0)

        Log.d("surabaya" ,"onCreate: $detail $id_pet")

        binding.arrowBack.setOnClickListener {
            finish()
        }

        binding.edtpet.setText(image)
        binding.btnUpload.setOnClickListener {

            val petname = binding.petNameEditText.text.toString().trim()
            val jenis_pet = binding.edtpet.text.toString().trim()

            if (type_pet!=null && gender_Pet!=null && tanggal!=null && petname.isNotEmpty() ){
                val intent = Intent(this, InfoPetStatus2Activity::class.java)
                intent.putExtra("pet_id", id_pet )
                intent.putExtra("pet_name", petname )
                intent.putExtra("gender", gender_Pet )
                intent.putExtra("detail", detail )
                intent.putExtra("tanggal", tanggal )

                Log.d("surabaya", "onCreate: $tanggal")
                startActivity(intent)
            }else{
                Snackbar.make(it,"Jangan kosongi kolom",3000).show()
            }
        }
        var petTypes: List<String>? = null
        if (image == "Cat") {
            petTypes = listOf(
                "Anggora",
                "Persia",
                "Siam",
                "Ragdoll",
                "Maine Coon",
                "Sphynx",
                "Russian Blue",
                "Other"
            )
        } else if (image == "Dog") {
            petTypes = listOf(
                "Alaskan Malamut",
                "Akita",
                "Beagle",
                "Belgian Malinos",
                "Boxer",
                "Cihuahua",
                "Dachshund",
                "Other"
            )

        }

        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, petTypes!!)

        binding.typePet.setAdapter(adapter)
        binding.typePet.threshold = 1

        binding.typePet.setOnItemClickListener { parent, view, position, id ->
            type_pet = parent.getItemAtPosition(position) as String
        }

        var genderPet = listOf("Male", "Female")

        val adapter_genderpet =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, genderPet)

        binding.genderPet.setAdapter(adapter_genderpet)
        binding.genderPet.threshold = 1

        binding.genderPet.setOnItemClickListener { parent, view, position, id ->
            gender_Pet = parent.getItemAtPosition(position) as String
        }

        binding.btndate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Membuat instance DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Aksi yang dijalankan saat tanggal dipilih

                    val timePickerDialog = TimePickerDialog(
                        this,
                        { _, selectedHour, selectedMinute ->
                            // Aksi yang dijalankan saat waktu dipilih

                            // Format tanggal dan waktu sesuai dengan kebutuhan
                            val formattedDate = String.format(
                                "%04d-%02d-%02d %02d:%02d:00",
                                selectedYear, selectedMonth + 1, selectedDay, selectedHour, selectedMinute
                            )

                            binding.date.text = formattedDate
                            tanggal = formattedDate
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    )

                    // Menampilkan TimePickerDialog setelah DatePickerDialog
                    timePickerDialog.show()
                },
                year,
                month,
                day
            )

            // Menampilkan DatePickerDialog
            datePickerDialog.show()
        }


    }
}
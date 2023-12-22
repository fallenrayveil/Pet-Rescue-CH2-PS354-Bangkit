package com.example.petrescuecapstone.ui

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.data.UiState
import com.example.petrescuecapstone.databinding.ActivityDetailPetOtherUserBinding
import com.example.petrescuecapstone.databinding.ActivityDetailPetUserBinding
import com.example.petrescuecapstone.databinding.FragmentConfirmBinding
import com.example.petrescuecapstone.response.MypetResponse
import com.example.petrescuecapstone.viewmodel.DetailPetUserViewModel
import com.example.petrescuecapstone.viewmodel.ViewModelFactory
import com.google.gson.Gson

class DetailPetOtherUser : AppCompatActivity() {
    private val viewModel by viewModels<DetailPetUserViewModel> {
        ViewModelFactory.getInstance(this)
    }

    lateinit var binding: ActivityDetailPetOtherUserBinding
    lateinit var progressDialog: ProgressDialog
    var mypetmodel: MypetResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_pet_other_user)
        binding.lifecycleOwner = this

        progressDialog = ProgressDialog(this)
        val gson = Gson()
        mypetmodel =
            gson.fromJson(intent.getStringExtra("mypetmodel"), MypetResponse::class.java)


        binding.idPet.text = "ID Pet : ${mypetmodel?.petId.toString()}"
        binding.pet.text = "Pet : ${mypetmodel?.petName.orEmpty()}"
        binding.namePet.text = "Name : ${mypetmodel?.petName.orEmpty()}"
        binding.Address.text = "Address : ${mypetmodel?.foundArea.orEmpty()} ${mypetmodel?.regency.orEmpty()} ${mypetmodel?.province.orEmpty()}"
        binding.Date.text = "Date : ${mypetmodel?.dateLostFound.orEmpty()}"
        binding.Type.text = "Type : ${mypetmodel?.type.orEmpty()}"
        binding.Gender.text = "Gender : ${mypetmodel?.gender.orEmpty()}"
        binding.Email.text = "Email : ${mypetmodel?.email.orEmpty()}"
        binding.Handphone.text = "Handphone : ${mypetmodel?.phoneNumber.orEmpty()}"
        binding.Reward.text = "Reward : ${mypetmodel?.reward.orEmpty()}"
        binding.detail.text = "Detail : ${mypetmodel?.detail.orEmpty()}"

        Glide.with(this)
            .load(mypetmodel!!.imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(binding.imageBanner)

        binding.arrowBack.setOnClickListener {
            finish()
        }


    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        loading(isLoading)
    }

    fun loading(status: Boolean) {
        if (status) {
            progressDialog.setTitle("Loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }

    }

    fun showcustomdialog(petid:Int) {
        val dialogBinding: FragmentConfirmBinding? =
            DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.fragment_confirm,
                null,
                false
            )

        val customDialog =
            AlertDialog.Builder(this, 0).create()

        customDialog.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            setView(dialogBinding?.root)
            setCancelable(true)
        }.show()

        dialogBinding?.btnno?.setOnClickListener {
            customDialog.dismiss()
        }

        dialogBinding?.btnyes?.setOnClickListener {
            viewModel.update_status(petid).observe(this) { result ->
                when (result) {
                    is UiState.Loading -> {
                        showLoading(true)
                    }

                    is UiState.Success -> {
                        showLoading(false)
                        showToast("Berhasil di update")
                        finish()
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
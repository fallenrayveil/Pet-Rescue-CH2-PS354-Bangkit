package com.example.petrescuecapstone.response

import com.google.gson.annotations.SerializedName

data class MypetResponse(

	@field:SerializedName("reward")
	val reward: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("regency")
	val regency: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("pet_name")
	val petName: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("found_area")
	val foundArea: String? = null,

	@field:SerializedName("date_lost_found")
	val dateLostFound: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("pet_id")
	val petId: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null

	)


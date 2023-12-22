package com.example.petrescuecapstone.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(
	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("pred_img")
	val predImg: String? = null,

	@field:SerializedName("pet_id")
	val petId: Int? = null
)

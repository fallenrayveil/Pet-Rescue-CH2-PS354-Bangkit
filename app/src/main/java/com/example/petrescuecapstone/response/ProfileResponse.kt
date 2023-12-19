package com.example.petrescuecapstone.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String
)

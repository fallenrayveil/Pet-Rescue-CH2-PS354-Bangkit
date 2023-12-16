package com.example.petrescuecapstone.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ResponseTest (
    @field:SerializedName("ResponseTest")
    val responseTest: List<ResponseTestItem>
)
@Parcelize
data class ResponseTestItem(

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("diskripsi")
    val diskripsi: String,

    @field:SerializedName("header1")
    val header1: String,

    @field:SerializedName("isih1")
    val isih1: String,

    @field:SerializedName("judul")
    val judul: String
) : Parcelable
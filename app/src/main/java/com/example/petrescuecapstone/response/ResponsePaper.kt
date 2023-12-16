package com.example.petrescuecapstone.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ResponsePaper (
    @field:SerializedName("ResponsePaper")
    val responsePaper: List<ResponsePaperItem>
)

@Parcelize
data class ResponsePaperItem(

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("header2")
    val header2: String,

    @field:SerializedName("diskripsi")
    val diskripsi: String,

    @field:SerializedName("header1")
    val header1: String,

    @field:SerializedName("isih2")
    val isih2: String,

    @field:SerializedName("isih1")
    val isih1: String,

    @field:SerializedName("judul")
    val judul: String
) : Parcelable

package com.example.petrescuecapstone.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

    @field:SerializedName("pagination")
	val pagination: Pagination? =null,

    @field:SerializedName("data")
	val data: List<DataItem>
)

data class DataItem(

	@field:SerializedName("image")
	val image: Any? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("published_at")
	val publishedAt: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class Pagination(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("count")
	val count: Int? = null
)

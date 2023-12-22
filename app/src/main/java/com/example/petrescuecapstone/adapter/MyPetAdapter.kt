package com.example.petrescuecapstone.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.response.MypetResponse
import com.example.petrescuecapstone.databinding.ItemRowPetBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

class MyPetAdapter : RecyclerView.Adapter<MyPetAdapter.MypetViewHolder>() {
    private val list = ArrayList<MypetResponse>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(pets: List<MypetResponse?>) {
        list.clear()
        list.addAll(pets.filterNotNull())
        notifyDataSetChanged()
    }

    inner class MypetViewHolder(val binding: ItemRowPetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var context = itemView.context
        fun bind(user: MypetResponse) {
            binding.apply {
                root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(user)
                }
                Glide.with(itemView)
                    .load(user.imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imagePet)
                tvName.text = user.petName
                tvDescriptionAddress.text = user.foundArea
                val tanggalyanglalu =  formatTimeAgo(user.dateLostFound!!)
                createAtTextView.text = tanggalyanglalu

                if (user.status == 0){
                    status.visibility = View.GONE
                }else{
                    status.visibility = View.VISIBLE
                }
            }
            Log.e(MyPetAdapter::class.java.simpleName, "User : ${user} ")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MypetViewHolder {
        val view = ItemRowPetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MypetViewHolder((view))
    }

    override fun onBindViewHolder(holder: MypetViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener { onItemClickCallback?.onItemClicked(list[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
//        val limit = 3
//        return min(list.size, limit)
        return list.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: MypetResponse)
    }

    private fun formatTimeAgo(timestamp: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        try {
            val date = sdf.parse(timestamp)

            // Set timezone to Jakarta, Indonesia
            val jakartaTimeZone = TimeZone.getTimeZone("Asia/Jakarta")
            val jakartaSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            jakartaSdf.timeZone = jakartaTimeZone

            val now = Date()

            val diff = now.time - date.time
            val seconds = abs(diff) / 1000
            val minutes = seconds / 60
            val hours = minutes / 60

            return when {
                seconds < 60 -> "$seconds detik yang lalu"
                minutes < 60 -> "$minutes menit yang lalu"
                hours < 24 -> "$hours jam yang lalu"
                else -> jakartaSdf.format(date)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }
}
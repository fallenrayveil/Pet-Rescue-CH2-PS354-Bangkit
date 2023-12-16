package com.example.petrescuecapstone.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.petrescuecapstone.response.DataItem
import com.example.petrescuecapstone.databinding.ItemListArticleBinding

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.UserViewHolder>() {
    private val list = ArrayList<DataItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: List<DataItem>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemListArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var context = itemView.context
        fun bind(user: DataItem) {
            binding.apply {
                root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(user)
                }
                Glide.with(itemView)
                    .load(user.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imgItemPhoto)
                tvTitleArticle.text = user.title
                tvPublisher.text = user.author
            }
            Log.e(ArticleAdapter::class.java.simpleName, "User : ${user} ")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemListArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener { onItemClickCallback?.onItemClicked(list[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
//        val limit = 3
//        return min(list.size, limit)
        return list.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: DataItem)
    }
}
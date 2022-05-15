package com.marta.bookworm.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marta.bookworm.databinding.ItemPhotoBinding
import com.marta.bookworm.api.model.response.ReviewResponse

class ResultAdapter(private val navigateToDetail: (String) -> Unit) :
    ListAdapter<ReviewResponse, ResultAdapter.ResultViewHolder>(ResultItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemPhotoBinding = ItemPhotoBinding.inflate(inflater, parent, false)
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val review = getItem(position)
        with(holder.binding){
            ivPhotoItem.loadImage(review.image)
            cvPhotoItem.layoutParams.height = cvPhotoItem.getWidth();
            ivPhotoItem.setOnClickListener {
                navigateToDetail(review.id)
            }
        }

    }


    inner class ResultViewHolder(val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root)
}

object ResultItemCallBack : DiffUtil.ItemCallback<ReviewResponse>() {
    override fun areItemsTheSame(oldItem: ReviewResponse, newItem: ReviewResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReviewResponse, newItem: ReviewResponse): Boolean {
        return oldItem.image == oldItem.image
    }

}
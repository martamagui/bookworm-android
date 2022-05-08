package com.marta.bookworm.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marta.bookworm.databinding.ItemFeedBinding
import com.marta.bookworm.model.response.ReviewResponse

class FeedAdapter :
    ListAdapter<ReviewResponse, FeedAdapter.ReviewViewHolder>(ReviewItemCallBack) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater =  LayoutInflater.from(parent.context)
        val binding: ItemFeedBinding = ItemFeedBinding.inflate(inflater, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        with(holder.binding) {
            ivReviewImage.loadImage(review.image)
            tvTitleItem.text = review.bookTitle
            tvDescriptionItem.text = review.reviewDescription
            tvScoreItem.text = review.score.toString()
            tvAuthorItem.text = review.bookAuthor
            //UserInfo
            //ibAvatarItem.loadImage(review.user.avatar)
            tvUsernameItem.text = "@queen"
        }
    }

    inner class ReviewViewHolder(val binding: ItemFeedBinding) :
        RecyclerView.ViewHolder(binding.root)

}

object ReviewItemCallBack : DiffUtil.ItemCallback<ReviewResponse>() {
    override fun areItemsTheSame(oldItem: ReviewResponse, newItem: ReviewResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReviewResponse, newItem: ReviewResponse): Boolean {
        return oldItem.reviewDescription == newItem.reviewDescription
    }

}
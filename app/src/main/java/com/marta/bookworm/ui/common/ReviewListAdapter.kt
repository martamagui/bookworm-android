package com.marta.bookworm.ui.common

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marta.bookworm.databinding.ItemFeedBinding
import com.marta.bookworm.model.response.ReviewResponse

class ReviewListAdapter: ListAdapter<ReviewResponse, ReviewListAdapter.ReviewViewHolder> (ReviewItemCallBack) {

    inner class ReviewViewHolder(val binding: ItemFeedBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        with(holder.binding){
            ivReviewImage.loadImage(review.image)
            tvTitleItem.text = review.bookTitle
            tvDescriptionItem.text = review.reviewDescription
            tvScoreItem.text = review.score.toString()
            tvAuthorItem.text = review.bookAuthor
            //UserInfo
            ibAvatarItem.loadImage(review.user.avatar)
            tvUsernameItem.text = review.user.userName
        }
    }

}

object  ReviewItemCallBack: DiffUtil.ItemCallback<ReviewResponse>(){
    override fun areItemsTheSame(oldItem: ReviewResponse, newItem: ReviewResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReviewResponse, newItem: ReviewResponse): Boolean {
        return oldItem.reviewDescription == newItem.reviewDescription
    }

}
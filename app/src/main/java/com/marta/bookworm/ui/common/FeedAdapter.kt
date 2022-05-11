package com.marta.bookworm.ui.common

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marta.bookworm.databinding.ItemFeedBinding
import com.marta.bookworm.model.response.ReviewResponse

class FeedAdapter(
    private val navigateToDetail: (String) -> Unit,
    private val navigateToUserProfile: (String) -> Unit,
    private val likeDislikeCall: (String) -> Unit,
    private val saveUnsavePost: (String) -> Unit
) :
    ListAdapter<ReviewResponse, FeedAdapter.ReviewViewHolder>(ReviewItemCallBack) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemFeedBinding = ItemFeedBinding.inflate(inflater, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        setActions(review, holder)
        with(holder.binding) {
            ivReviewImage.loadImage(review.image)
            tvTitleItem.text = review.bookTitle
            tvDescriptionItem.text = review.reviewDescription
            tvScoreItem.text = review.score.toString()
            tvAuthorItem.text = review.bookAuthor
            //UserInfo
            tvUsernameItem.text =review.userId.userName
            ibAvatarItem.loadImage(review.userId.avatar)
            //OnclickListeners
        }
    }

    private fun setActions(item: ReviewResponse, holder: ReviewViewHolder) {
        with(holder.binding) {
            //TODO shop, like and save
            var user = "Cambiar"
            layoutUser.setOnClickListener {
                navigateToUserProfile(user)
            }
            ibToDetail.setOnClickListener {
                Log.d("Detail", item.id)
                navigateToDetail(item.id)
            }
            cvLikeItem.setOnClickListener {
                likeDislikeCall(item.id)
            }
            cvSaveItem.setOnClickListener {
                saveUnsavePost(item.id)
            }
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
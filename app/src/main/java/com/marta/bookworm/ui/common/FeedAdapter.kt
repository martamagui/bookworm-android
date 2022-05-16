package com.marta.bookworm.ui.common

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marta.bookworm.R
import com.marta.bookworm.databinding.ItemFeedBinding
import com.marta.bookworm.api.model.response.ReviewResponse


class FeedAdapter(
    private val navigateToDetail: (String) -> Unit,
    private val navigateToUserProfile: (String) -> Unit,
    private val likeDislikeCall: (String) -> Unit,
    private val saveUnsavePost: (String) -> Unit,
    private val goToAmazon: (String) -> Unit,
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
        setButtonsImages(review, holder)
        with(holder.binding) {
            ivReviewImage.loadImage(review.image)
            tvTitleItem.text = review.bookTitle
            tvDescriptionItem.text = review.reviewDescription
            tvScoreItem.text = review.score.toString()
            tvAuthorItem.text = review.bookAuthor
            tvLikesAmountItem.text = review.likesAmount.toString()
            tvUsernameItem.text = review.userId.userName
            ibAvatarItem.loadImage(review.userId.avatar)
        }
    }

    private fun setButtonsImages(item: ReviewResponse, holder: ReviewViewHolder) {
        with(holder.binding) {
            ivLike.setImageResource(
                if (item.liked == true) {
                    R.drawable.ic_baseline_favorite_24
                } else {
                    R.drawable.ic_baseline_favorite_border_24
                }
            )
            ivSave.setImageResource(
                if (item.saved == true) {
                    R.drawable.ic_baseline_bookmark_24
                } else {
                    R.drawable.ic_baseline_bookmark_border_24
                }
            )
        }
    }

    private fun setActions(item: ReviewResponse, holder: ReviewViewHolder) {
        with(holder.binding) {
            //TODO shop, like and save
            Log.e("ID", "${item.id}")
            layoutUser.setOnClickListener {
                navigateToUserProfile(item.userId.id)
            }
            ibToDetail.setOnClickListener {
                Log.d("Detail", item.id)
                navigateToDetail(item.id)
            }

            tvDescriptionItem.setOnClickListener {
                navigateToDetail(item.id)
            }
            cvShopItem.setOnClickListener { goToAmazon("${item.bookTitle} ${item.bookAuthor}") }
            cvLikeItem.setOnClickListener {
                likeDislikeCall(item.id)
                if (item.liked == true) {
                    item.likesAmount = item.likesAmount?.minus(1)
                } else {
                    item.likesAmount = item.likesAmount?.plus(1)
                }
                item.liked = item.liked == null || item.liked == false
                ivLike.setImageResource(
                    if (item.liked == true) {
                        R.drawable.ic_baseline_favorite_24
                    } else {
                        R.drawable.ic_baseline_favorite_border_24
                    }
                )
                tvLikesAmountItem.text = item.likesAmount.toString()
            }
            cvSaveItem.setOnClickListener {
                saveUnsavePost(item.id)
                item.saved = item.saved == null || item.saved == false
                ivSave.setImageResource(
                    if (item.saved == true) {
                        R.drawable.ic_baseline_bookmark_24
                    } else {
                        R.drawable.ic_baseline_bookmark_border_24
                    }
                )
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
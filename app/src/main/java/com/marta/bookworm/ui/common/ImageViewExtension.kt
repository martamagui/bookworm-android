package com.marta.bookworm.ui.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.marta.bookworm.R

fun ImageView.loadImage(imageUrl: String?) {
    if(imageUrl!=null){
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.grad_default_img)
            .error(R.drawable.grad_default_img)
            .into(this)
    }
}
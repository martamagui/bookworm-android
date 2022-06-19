package com.marta.bookworm.ui.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.marta.bookworm.R

fun ImageView.loadImage(imageUrl: String?) {
    if(imageUrl!=null){
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.img)
            .error(R.drawable.img)
            .into(this)
    }
}
package com.example.finalproject.presentation.extension

import com.bumptech.glide.Glide
import com.example.finalproject.R
import de.hdodenhof.circleimageview.CircleImageView

fun CircleImageView.loadImage(url: String?) {
    if (url != null) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .into(this)
    }
}
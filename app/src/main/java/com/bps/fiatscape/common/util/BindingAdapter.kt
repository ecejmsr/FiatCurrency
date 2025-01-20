package com.bps.fiatscape.common.util

import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter

@BindingAdapter("imgSrc")
fun bindImageSource(imageView: ImageView, resource: Int?) {
    resource?.let {
        imageView.setImageDrawable(AppCompatResources.getDrawable(imageView.context, resource))
    }
}

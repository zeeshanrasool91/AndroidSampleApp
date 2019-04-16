

package com.example.myapplication.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatImageView;

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }


    @BindingAdapter("imageUrl")
    public static void setImageUrl(AppCompatImageView imageView, String url) {
        if (url == null) {
            imageView.setImageDrawable(null);
        } else {
            AppUtils.getInstance().loadImage(imageView, url);
        }
    }

}

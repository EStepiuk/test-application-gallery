package com.yevhen_stepiuk.testgallery.presentation.main.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.bumptech.glide.Glide
import com.yevhen_stepiuk.testgallery.GalleryApp
import com.yevhen_stepiuk.testgallery.R
import kotlinx.android.synthetic.main.gif_dilaog.*


class GifDialog : DialogFragment() {

    companion object {
        private const val GIF_URI_STRING = "GIF_URI_STRING"

        fun newInstance(gifUriString: String) = GifDialog().apply {
            arguments = Bundle().apply { putString(GIF_URI_STRING, gifUriString) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GalleryApp.getComponent(activity).inject(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) = super.onCreateDialog(savedInstanceState).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.gif_dilaog, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(activity)
                .load(arguments.getString(GIF_URI_STRING))
                .into(imgGif)
    }
}
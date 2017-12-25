package com.yevhen_stepiuk.testgallery.common

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.yevhen_stepiuk.testgallery.BuildConfig
import com.yevhen_stepiuk.testgallery.GalleryApp
import java.io.InputStream


@GlideModule
class GlideConfig : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        if (BuildConfig.DEBUG) builder.setLogLevel(Log.DEBUG)
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, 100 * 1024 * 1024))
    }

    override fun registerComponents(context: Context, glide: Glide?, registry: Registry) {
        val okHttpClient = GalleryApp.getComponent(context).provideOkHttpClient()
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
    }
}
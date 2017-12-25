package com.yevhen_stepiuk.testgallery.injection.app

import android.content.Context
import com.google.gson.Gson
import com.yevhen_stepiuk.testgallery.BuildConfig
import com.yevhen_stepiuk.testgallery.R
import com.yevhen_stepiuk.testgallery.data.api.GalleryAPI
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File


@Module
class NetModule {

    @Provides
    @AppScope
    internal fun provideOkHttpCache(context: Context): Cache =
            Cache(File(context.externalCacheDir, "okhttp-cache"), 20 * 1024 * 1024)

    @Provides
    @AppScope
    internal fun provideOkHttpClient(cache: Cache): OkHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .let { if (BuildConfig.DEBUG) it.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) else it }
            .build()

    @Provides
    internal fun provideRetrofitBuilder(client: OkHttpClient, gson: Gson): Retrofit.Builder = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)

    @Provides
    @AppScope
    internal fun provideGalleryAPI(retrofitBuilder: Retrofit.Builder, context: Context): GalleryAPI =
            retrofitBuilder
                    .baseUrl(context.getString(R.string.api_base_url))
                    .build()
                    .create(GalleryAPI::class.java)
}
package com.yevhen_stepiuk.testgallery.data.gallery

import com.yevhen_stepiuk.testgallery.common.Mapper
import com.yevhen_stepiuk.testgallery.data.api.body.GifResponse
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.Gif


object GifMapper : Mapper<GifResponse, Gif> {

    override fun map(from: GifResponse) = Gif(from.gif)
}
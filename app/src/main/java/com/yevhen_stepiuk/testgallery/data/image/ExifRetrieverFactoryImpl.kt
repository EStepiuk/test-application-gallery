package com.yevhen_stepiuk.testgallery.data.image

import android.content.ContentResolver
import android.net.Uri
import android.support.media.ExifInterface
import com.yevhen_stepiuk.testgallery.domain.image.ExifRetriever
import com.yevhen_stepiuk.testgallery.domain.location.entity.GeoPos


class ExifRetrieverFactoryImpl(private val contentResolver: ContentResolver) : ExifRetriever.Factory {

    private val emptyExifRetriever: ExifRetriever = EmptyExifRetriever()

    override fun from(imageUriString: String): ExifRetriever {
        return try {
            val inputStream = contentResolver.openInputStream(Uri.parse(imageUriString))
            val exifRetriever = ExifRetrieverImpl(ExifInterface(inputStream))
            inputStream.close()
            exifRetriever
        } catch (e: Exception) {
            emptyExifRetriever
        }
    }

    private class ExifRetrieverImpl(val exifInterface: ExifInterface) : ExifRetriever {

        override val geoPos: GeoPos? get() = exifInterface.latLong?.let { GeoPos(it[0], it[1]) }
    }

    private class EmptyExifRetriever : ExifRetriever {
        override val geoPos: GeoPos? get() = null
    }
}
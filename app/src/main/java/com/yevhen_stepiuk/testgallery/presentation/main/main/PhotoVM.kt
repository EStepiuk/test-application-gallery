package com.yevhen_stepiuk.testgallery.presentation.main.main

import android.os.Parcel
import android.os.Parcelable


data class PhotoVM(val photoUriString: String, val weather: String, val address: String) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(photoUriString)
        writeString(weather)
        writeString(address)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PhotoVM> = object : Parcelable.Creator<PhotoVM> {
            override fun createFromParcel(source: Parcel): PhotoVM = PhotoVM(source)
            override fun newArray(size: Int): Array<PhotoVM?> = arrayOfNulls(size)
        }
    }
}
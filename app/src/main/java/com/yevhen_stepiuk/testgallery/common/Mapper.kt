package com.yevhen_stepiuk.testgallery.common


interface Mapper<in From, out To> {

    fun map(from: From): To
}
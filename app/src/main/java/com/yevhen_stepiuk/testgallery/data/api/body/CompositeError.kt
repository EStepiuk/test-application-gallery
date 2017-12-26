package com.yevhen_stepiuk.testgallery.data.api.body


data class CompositeError(val children: Map<String, Errors>?)

data class Errors(val errors: List<String>?)
package com.yevhen_stepiuk.testgallery.domain.authorization.entity


data class SignUpParam(val username: String,
                       val email: String,
                       val password: String,
                       val avatarUriString: String?)
package com.yevhen_stepiuk.testgallery.data.authorization

import com.yevhen_stepiuk.testgallery.common.Mapper
import com.yevhen_stepiuk.testgallery.data.api.body.Errors
import com.yevhen_stepiuk.testgallery.domain.authorization.errors.*
import com.yevhen_stepiuk.testgallery.extension.concatToSingleString


object AuthorizationExceptionMapper : Mapper<Map<String, Errors>, AuthorizationException?> {

    override fun map(from: Map<String, Errors>): AuthorizationException? {
        val email = from["email"]?.errors?.concatToSingleString()
        if (email?.isNotEmpty() == true) return EmailError(email)

        val username = from["username"]?.errors?.concatToSingleString()
        if (username?.isNotEmpty() == true) return UsernameError(username)

        val password = from["password"]?.errors?.concatToSingleString()
        if (password?.isNotEmpty() == true) return PasswordError(password)

        val avatar = from["avatar"]?.errors?.concatToSingleString()
        if (avatar?.isNotEmpty() == true) return AvatarError(avatar)

        return null
    }
}
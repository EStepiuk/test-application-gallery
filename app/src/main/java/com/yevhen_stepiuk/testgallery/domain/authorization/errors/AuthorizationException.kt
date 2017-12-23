package com.yevhen_stepiuk.testgallery.domain.authorization.errors


open class AuthorizationException(override val message: String) : Throwable(message)

class EmailError(message: String) : AuthorizationException(message)
class UsernameError(message: String) : AuthorizationException(message)
class PasswordError(message: String) : AuthorizationException(message)
class AvatarError(message: String) : AuthorizationException(message)
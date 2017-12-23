package com.yevhen_stepiuk.testgallery.data.authorization

import android.content.ContentResolver
import android.net.Uri
import com.google.gson.Gson
import com.yevhen_stepiuk.testgallery.data.api.GalleryAPI
import com.yevhen_stepiuk.testgallery.data.api.body.CreateError
import com.yevhen_stepiuk.testgallery.data.api.body.ErrorBody
import com.yevhen_stepiuk.testgallery.data.api.body.LoginResponse
import com.yevhen_stepiuk.testgallery.data.preferences.SharedPrefsWrapper
import com.yevhen_stepiuk.testgallery.domain.authorization.AuthorizationManager
import com.yevhen_stepiuk.testgallery.domain.authorization.entity.LoginParam
import com.yevhen_stepiuk.testgallery.domain.authorization.entity.SignUpParam
import com.yevhen_stepiuk.testgallery.domain.authorization.errors.*
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException


class AuthorizationManagerImpl(private val galleryAPI: GalleryAPI,
                               private val contentResolver: ContentResolver,
                               private val prefsWrapper: SharedPrefsWrapper,
                               private val gson: Gson) : AuthorizationManager {

    override fun login(loginParam: LoginParam) = galleryAPI
            .login(loginParam.email, loginParam.password)
            .onErrorResumeNext {
                if (it is HttpException) {
                    val errorBody = gson.fromJson(it.response().errorBody()?.charStream(), ErrorBody::class.java)
                    errorBody?.error?.let { return@onErrorResumeNext Single.error<LoginResponse>(AuthorizationException(it)) }
                }
                return@onErrorResumeNext Single.error<LoginResponse>(it)
            }
            .doOnSuccess {
                prefsWrapper.apiToken = it.token
                prefsWrapper.avatarUriString = it.avatar
            }
            .toCompletable()

    override fun signUp(signUpParam: SignUpParam) = Single
            .defer {
                val avatarRequestBody = signUpParam.avatarUriString?.let {
                    val rb = RequestBody.create(MediaType.parse("image/*"), contentResolver.openInputStream(Uri.parse(it)).readBytes())
                    MultipartBody.Part.createFormData("avatar", "${signUpParam.username}_avatar", rb)
                }
                galleryAPI.createUser(signUpParam.username, signUpParam.email, signUpParam.password, avatarRequestBody)
            }
            .onErrorResumeNext {
                if (it is HttpException) {
                    val createError = gson.fromJson(it.response().errorBody()?.charStream(), CreateError::class.java)
                    val throwable = createError?.children?.let {
                        when {
                            it.emailErrors.error != null -> EmailError(it.emailErrors.error)
                            it.usernameErrors.error != null -> UsernameError(it.usernameErrors.error)
                            it.passwordErrors.error != null -> PasswordError(it.passwordErrors.error)
                            it.avatarErrors.error != null -> AvatarError(it.avatarErrors.error)
                            else -> null
                        }
                    }
                    throwable?.let { return@onErrorResumeNext Single.error<LoginResponse>(it) }
                }
                return@onErrorResumeNext Single.error<LoginResponse>(it)
            }
            .doOnSuccess {
                prefsWrapper.apiToken = it.token
                prefsWrapper.avatarUriString = it.avatar
            }
            .toCompletable()
}
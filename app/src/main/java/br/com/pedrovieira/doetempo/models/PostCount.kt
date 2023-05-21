package br.com.pedrovieira.doetempo.models

import com.google.gson.annotations.SerializedName

data class PostCount (
    val comment: Int? = null,
    @SerializedName("post_ngo")
    val postNgo: Int? = null,
    @SerializedName("post_photo")
    val postPhoto: Int? = null,
    @SerializedName("post_user")
    val postUser: Int? = null,
    @SerializedName("post_likes")
    val postLikes: Int? = null
)

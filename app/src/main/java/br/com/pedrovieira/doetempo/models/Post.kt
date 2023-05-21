package br.com.pedrovieira.doetempo.models

import com.google.gson.annotations.SerializedName

data class Post (
    val id: String? = null,
    val content: String? = null,
    @SerializedName("post_likes")
    val postLikes: List<PostLike?>? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("post_photo")
    val postPhoto: List<PostPhoto>? = null,
    val comment: List<Comment>? = null,
    val count: PostCount? = null
)
package br.com.pedrovieira.doetempo.models.responses

import br.com.pedrovieira.doetempo.models.Post
import com.google.gson.annotations.SerializedName

data class AllPostResponse(
    @SerializedName("all_posts")
    val allPosts: List<Post>? = null
)

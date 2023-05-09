package br.com.pedrovieira.doetempo.datastore.models

data class JwtPayload (
    val id: String? = null,
    val email: String? = null,
    val type: String? = null,
    val iat: Long? = null,
    val exp: Long? = null
)
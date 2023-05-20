package br.com.pedrovieira.doetempo.models.responses

import br.com.pedrovieira.doetempo.datastore.models.User

data class UserCreatedResponse(
    val message: String? = null,
    val payload: User? = null
)

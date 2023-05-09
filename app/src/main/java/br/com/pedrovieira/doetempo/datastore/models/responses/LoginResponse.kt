package br.com.pedrovieira.doetempo.datastore.models.responses

import br.com.pedrovieira.doetempo.datastore.models.User

data class LoginResponse(
    var token: String,
    var data: User,
)

package br.com.pedrovieira.doetempo.models.responses

import br.com.pedrovieira.doetempo.models.User

data class LoginResponse(
    var token: String,
    var data: User,
)

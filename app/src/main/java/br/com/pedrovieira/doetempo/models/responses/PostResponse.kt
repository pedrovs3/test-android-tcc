package br.com.pedrovieira.doetempo.models.responses

import br.com.pedrovieira.doetempo.models.payload.PayloadPublication

data class PostResponse(
    val message: String? = null,
    val payload: PayloadPublication? = null
)

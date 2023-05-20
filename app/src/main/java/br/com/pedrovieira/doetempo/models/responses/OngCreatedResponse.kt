package br.com.pedrovieira.doetempo.models.responses

import br.com.pedrovieira.doetempo.models.payload.PayloadCreateOng

data class OngCreatedResponse(
    val message: String? = null,
    val payload: PayloadCreateOng? = null
)

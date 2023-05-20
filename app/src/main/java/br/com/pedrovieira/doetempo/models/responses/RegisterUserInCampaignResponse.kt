package br.com.pedrovieira.doetempo.models.responses

import br.com.pedrovieira.doetempo.models.payload.PayloadRegisterUserInCampaign

data class RegisterUserInCampaignResponse(
    val message: String? = null,
    val data: PayloadRegisterUserInCampaign? = null
)

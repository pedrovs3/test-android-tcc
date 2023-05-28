package br.com.pedrovieira.doetempo.models.responses

import br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign

data class CampaignByNameResponse(
    val message: String? = null,
    val payload: List<Campaign>? = null
)

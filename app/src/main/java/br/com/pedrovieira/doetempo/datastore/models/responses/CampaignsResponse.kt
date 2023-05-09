package br.com.pedrovieira.doetempo.datastore.models.responses

import br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign

data class CampaignsResponse (
    var campaigns: List<Campaign>
)
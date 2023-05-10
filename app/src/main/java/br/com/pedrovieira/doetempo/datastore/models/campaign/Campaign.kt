package br.com.pedrovieira.doetempo.datastore.models.campaign

import br.com.pedrovieira.doetempo.datastore.models.Address
import br.com.pedrovieira.doetempo.datastore.models.Ngo
import com.google.gson.annotations.SerializedName

data class Campaign (
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    @SerializedName("begin_date")
    val beginDate: String? = null,
    @SerializedName("end_date")
    val endDate: String? = null,
    @SerializedName("home_office")
    val homeOffice: Boolean? = null,
    val prerequisites: String? = null,
    @SerializedName("how_to_contribute")
    val howToContribute: String? = null,
    val ngo: Ngo? = null,
    @SerializedName("campaign_causes")
    val campaignCauses: List<CampaignCause>? = null,
    @SerializedName("campaign_address")
    val campaignAddress: Address? = null,
    @SerializedName("campaign_photos")
    val campaignPhotos: List<CampaignPhoto>? = null,
    @SerializedName("campaign_participants")
    val campaignParticipants: List<CampaignParticipant>? = null,
)
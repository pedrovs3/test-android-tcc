package br.com.pedrovieira.doetempo.datastore.models

import br.com.pedrovieira.doetempo.datastore.models.dto.PhoneDTO
import com.google.gson.annotations.SerializedName

data class Phone (
    @SerializedName("tbl_phone") var phone: PhoneDTO
)

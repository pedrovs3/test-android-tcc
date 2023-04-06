package br.com.pedrovieira.doetempo.models

import br.com.pedrovieira.doetempo.models.dto.PhoneDTO
import com.google.gson.annotations.SerializedName

data class Phone (
    @SerializedName("tbl_phone") var phone: PhoneDTO
)

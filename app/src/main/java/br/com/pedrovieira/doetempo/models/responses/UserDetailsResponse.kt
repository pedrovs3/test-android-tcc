package br.com.pedrovieira.doetempo.models.responses

import br.com.pedrovieira.doetempo.datastore.models.Address
import br.com.pedrovieira.doetempo.datastore.models.Phone
import br.com.pedrovieira.doetempo.datastore.models.User
import br.com.pedrovieira.doetempo.datastore.models.dto.GenderDTO
import br.com.pedrovieira.doetempo.models.Count
import br.com.pedrovieira.doetempo.models.UserAddress
import br.com.pedrovieira.doetempo.models.UserDetails
import com.google.gson.annotations.SerializedName

data class UserDetailsResponse(
    val user : UserDetails? = null
)

package br.com.pedrovieira.doetempo.models

import br.com.pedrovieira.doetempo.datastore.models.dto.GenderDTO

data class AllGenders(
    val genders: List<GenderDTO>? = null
)

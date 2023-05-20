package br.com.pedrovieira.doetempo.models.payload

data class PayloadCreateOng (
    val id: String? = null,
    val name: String? = null,
    val cnpj: String? = null,
    val foundationDate: String? = null,
    val description: Any? = null,
    val email: String? = null,
    val password: String? = null,
    val idType: String? = null,
    val photoURL: String? = null,
    val bannerPhoto: String? = null,
    val createdAt: String? = null
)
package br.com.pedrovieira.doetempo.helpers
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun convertIsoStringToLocalDate(isoString: String): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
    return try {
        LocalDate.parse(isoString, formatter)
    } catch (e: Exception) {
        null
    }
}
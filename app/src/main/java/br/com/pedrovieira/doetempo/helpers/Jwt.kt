package br.com.pedrovieira.doetempo.helpers

import android.util.Base64
import br.com.pedrovieira.doetempo.datastore.models.JwtPayload
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.nio.charset.StandardCharsets

class Jwt(private val token: String) {

    private val userData: JsonObject by lazy {
        val userData = String(Base64.decode(token.split(".")[1], Base64.DEFAULT), StandardCharsets.UTF_8)
        JsonParser.parseString(userData).asJsonObject
    }

    fun getUserData(): JwtPayload {
        gson.toJson(userData, Jwt::class.java)
        return gson.fromJson(userData, JwtPayload::class.java)
    }

    fun isExpired(): Boolean {
        return userData.asJsonObject.get("exp").asLong < (System.currentTimeMillis() / 1000)
    }

    companion object {

        @JvmStatic
        private val gson = Gson()
    }
}

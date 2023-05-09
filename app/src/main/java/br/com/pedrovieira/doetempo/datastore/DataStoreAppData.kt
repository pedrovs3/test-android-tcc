package br.com.pedrovieira.doetempo.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.auth0.android.jwt.JWT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreAppData(private val context: Context) {

    // Uma unica instancia
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("appData")
        val TOKEN_JWT = stringPreferencesKey("token")
        val ID_USER = stringPreferencesKey("id_user")
        val TYPE = stringPreferencesKey("type_user")
        val NAME_USER = stringPreferencesKey("name_user")
    }

    // Pegar o token
    val getToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_JWT] ?: ""
    }

    val getName: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[NAME_USER] ?: ""
    }

    val getType: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TYPE] ?: ""
    }

    val getIdUser: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[ID_USER] ?: ""
    }

    suspend fun saveIdUser(idUser: String) {
        context.dataStore.edit {preferences ->
            preferences[ID_USER] = idUser
        }
    }

    // Salvar o token no dataStore
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_JWT] = token
        }
    }

    suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME_USER] = name
        }
    }

    suspend fun saveType(type: String) {
        context.dataStore.edit { preferences ->
            preferences[TYPE] = type
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_JWT)
        }
    }
}
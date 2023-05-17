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
        val NAME_REGISTER = stringPreferencesKey("name_register")
        val EMAIL_REGISTER = stringPreferencesKey("email_register")
        val PASSWORD_REGISTER = stringPreferencesKey("password_register")
        val BIRTHDATE_REGISTER = stringPreferencesKey("birthdate_register")
        val GENDER_REGISTER = stringPreferencesKey("gender_register")
    }

    // Pegar o token
    val getToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_JWT] ?: ""
    }

    val getGenderRegister: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[GENDER_REGISTER] ?: ""
    }
    val getNameRegister: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[NAME_REGISTER] ?: ""
    }
    val getEmailRegister: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[EMAIL_REGISTER] ?: ""
    }
    val getPasswordRegister: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PASSWORD_REGISTER] ?: ""
    }
    val getBirthdateRegister: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[BIRTHDATE_REGISTER] ?: ""
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
    suspend fun saveGenderRegister(genderId: String) {
        context.dataStore.edit {preferences ->
            preferences[GENDER_REGISTER] = genderId
        }
    }
    suspend fun saveBirthdateRegister(birthdate: String) {
        context.dataStore.edit {preferences ->
            preferences[BIRTHDATE_REGISTER] = birthdate
        }
    }
    suspend fun saveNameRegister(name: String) {
        context.dataStore.edit {preferences ->
            preferences[NAME_REGISTER] = name
        }
    }
    suspend fun saveEmailRegister(email: String) {
        context.dataStore.edit {preferences ->
            preferences[EMAIL_REGISTER] = email
        }
    }
    suspend fun savePasswordRegister(password: String) {
        context.dataStore.edit {preferences ->
            preferences[PASSWORD_REGISTER] = password
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
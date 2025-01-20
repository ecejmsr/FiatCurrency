package com.bps.fiatscape.common.sharedPref

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class EncryptedSharedPrefRepoImpl(val context: Context): EncryptedSharePrefRepo {
    private val encryptedSharePref by lazy {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        EncryptedSharedPreferences.create(
            SHARED_PREF_NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun setKeyValueString(key: String, payload: String) {
        encryptedSharePref.edit().putString(key, payload).apply()
    }

    override fun getKeyValueString(key: String): String? {
        val stringVal = encryptedSharePref.let {
            if (encryptedSharePref.contains(key)) {
                encryptedSharePref.getString(key, null)
            } else {
                null
            }
        }

        return stringVal
    }

    companion object {
        const val SHARED_PREF_NAME = "HIRE_ME_PLS"
        const val FAV_KEY = "FAVES"
    }
}

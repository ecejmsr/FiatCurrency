package com.bps.fiatscape.common.sharedPref

interface EncryptedSharePrefRepo {
    fun setKeyValueString(key: String, payload: String)

    fun getKeyValueString(key: String): String?
}

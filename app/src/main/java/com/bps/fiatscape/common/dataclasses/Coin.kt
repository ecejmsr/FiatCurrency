package com.bps.fiatscape.common.dataclasses

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Coin(
    val id: String?,
    val name: String?,
    val symbol: String?,
    val rank: Int = 999,
    @Json(name = "is_new")
    val isNew: Boolean = false,
    @Json(name = "is_active")
    val isActive: Boolean = false,
    val type: String?
) : Parcelable

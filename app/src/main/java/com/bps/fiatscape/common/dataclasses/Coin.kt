package com.bps.fiatscape.common.dataclasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Coin(
    val id: String?,
    val name: String?,
    val symbol: String?,
    val rank: Int = 999,
    @SerialName("is_new")
    val isNew: Boolean = false,
    @SerialName("is_active")
    val isActive: Boolean = false,
    val type: String?
): Parcelable

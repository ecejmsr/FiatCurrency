package com.bps.fiatscape.common.dataclasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val is_new: Boolean,
    val is_active: Boolean,
    val type: String
): Parcelable

package com.bps.fiatscape.common.dataclasses

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Ohlc(
    @Json(name = "time_open")
    val timeOpen: String?,
    @Json(name = "time_close")
    val timeClose: String?,
    val open: Long = 0,
    val high: Long = 0,
    val low: Long = 0,
    val close: Long = 0,
    val volume: Long = 0,
    @Json(name = "market_cap")
    val marketCap: Long = 0
) : Parcelable

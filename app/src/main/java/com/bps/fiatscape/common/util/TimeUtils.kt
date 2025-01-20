package com.bps.fiatscape.common.util

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.bps.fiatscape.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeUtils {
    fun refreshDataDate(_lastRefreshed: MutableLiveData<String>, app: Context) {
        val date = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        _lastRefreshed.postValue(app.getString(R.string.last_refreshed, dateFormat.format(date)))
    }
}

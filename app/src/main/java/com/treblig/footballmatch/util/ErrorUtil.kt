package com.treblig.footballmatch.util

import android.content.Context
import android.text.TextUtils
import com.treblig.footballmatch.R
import org.json.JSONException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorUtil {
    fun getTitleError(context: Context, throwable: Throwable): String {
        return when (throwable) {
            is UnknownHostException -> context.getString(R.string.error_title_no_internet)
            is SocketException -> context.getString(R.string.error_title_connection_unstable)
            is SocketTimeoutException -> context.getString(R.string.error_title_timeout)
            is JSONException -> context.getString(R.string.error_response)
            else -> if (throwable.message?.isNotEmpty()!!) throwable.message!! else ""
        }
    }

    fun getMessageError(context: Context, throwable: Throwable): String {
        return when (throwable) {
            is UnknownHostException -> context.getString(R.string.error_message_no_internet)
            is SocketException -> context.getString(R.string.error_message_connection_unstable)
            is SocketTimeoutException -> context.getString(R.string.error_message_timeout)
            is JSONException -> context.getString(R.string.error_response_message)
            else -> if (throwable.message?.isNotEmpty()!!) throwable.message!! else ""
        }
    }
}







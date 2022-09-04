package com.example.sportsstore.common

import android.app.ActivityOptions
import com.example.sportsstore.R
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber

class SportsExceptionMapper {

    companion object {
        fun map(throwable: Throwable): SportsException {
            if (throwable is HttpException) {
                try {
                    val errorJsonObject = JSONObject(throwable.response()?.errorBody()!!.string())
                    val errorMessage = errorJsonObject.getString("message")
                    return SportsException(
                        if (throwable.code() == 401) SportsException.Type.AUTH else SportsException.Type.SIMPLE,
                        serverMessage = errorMessage
                    )
                } catch (exception: Exception) {
                    Timber.e(exception)
                }
            }
            return SportsException(SportsException.Type.SIMPLE, R.string.unknown_error)
        }
    }
}
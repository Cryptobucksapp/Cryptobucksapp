package com.cryptobucksapp.cryptobucks.utils.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseRemoteRepository {

    companion object {
        private const val TAG = "BaseRemoteRepository"
        private const val MESSAGE_KEY = "message"
        private const val FIELDS_ERRORS = "fieldsErrors"
        private const val CONSTRAINS = "constraints"
        private const val ERROR_KEY = "error"
    }

    /**
     * Function that executes the given function on Dispatchers.IO context and switch to Dispatchers.Main context when an error occurs
     * @param callFunction is the function that is returning the wanted object. It must be a suspend function. Eg:
     * override suspend fun loginUser(body: LoginUserBody, emitter: RemoteErrorEmitter): LoginUserResponse?  = safeApiCall( { authApi.loginUser(body)} , emitter)
     * @param emitter is the interface that handles the error messages. The error messages must be displayed on the MainThread, or else they would throw an Exception.
     */
    suspend inline fun <T> safeApiCall(emitter: RemoteErrorEmitter, crossinline callFunction: suspend () -> T): T? {
        return try{
            val myObject = withContext(Dispatchers.IO){ callFunction.invoke() }
            myObject
        }catch (e: Exception){
            mError(e, emitter)
            null
        }
    }

    suspend fun mError(
        e: Exception,
        emitter: RemoteErrorEmitter
    ) {
        withContext(Dispatchers.Main) {
            e.printStackTrace()
            when (e) {
                is HttpException -> {
                    when {
                        e.code() == 401 || e.code() == 400 -> {
                            val body = e.response()?.errorBody()
                            emitter.onError(getErrorMessage(body))
                        }
                    }
                }
                is SocketTimeoutException -> emitter.onError(ErrorType.TIMEOUT)
                is IOException -> emitter.onError(ErrorType.NETWORK)
                else -> emitter.onError(ErrorType.UNKNOWN)
            }
        }
    }

    fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            when {
                jsonObject.has(FIELDS_ERRORS) -> jsonObject.getJSONArray(
                    FIELDS_ERRORS
                ).getJSONObject(0).getJSONArray(
                    CONSTRAINS
                ).getString(0)
                jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(
                    MESSAGE_KEY
                )
                jsonObject.has(ERROR_KEY) -> jsonObject.getString(
                    ERROR_KEY
                )
                else -> "Something wrong happened"
            }
        } catch (e: Exception) {
            "Something wrong happened"
        }
    }
}
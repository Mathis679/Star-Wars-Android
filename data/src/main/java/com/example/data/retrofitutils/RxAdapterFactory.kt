package com.example.data.retrofitutils

import android.content.Context
import android.util.Log
import com.example.domain.exception.NoInternetException
import com.example.utils.NetworkUtil
import io.reactivex.*
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class RxAdapterFactory(private val context: Context) :
        CallAdapter.Factory() {

    private val original: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    override fun get(
            returnType: Type, annotations: Array<Annotation>,
            retrofit: Retrofit
    ): CallAdapter<Any, Any> {
        return RxCallAdapterWrapper(
                returnType,
                (original.get(returnType, annotations, retrofit) as CallAdapter<Any, Any>?)!!
        )
    }

    inner class RxCallAdapterWrapper(
            private val returnType: Type,
            private val wrapped: CallAdapter<Any, Any>
    ) : CallAdapter<Any, Any> {

        override fun responseType(): Type {
            return wrapped.responseType()
        }

        override fun adapt(call: Call<Any>): Any? {
            val rawType = getRawType(returnType)

            val isObservable = rawType == Observable::class.java
            val isFlowable = rawType == Flowable::class.java
            val isSingle = rawType == Single::class.java
            val isMaybe = rawType == Maybe::class.java
            if (rawType != Observable::class.java && !isFlowable && !isSingle && !isMaybe) {
                return null
            }
            if (returnType !is ParameterizedType) {
                val name = when {
                    isFlowable -> "Flowable"
                    isSingle -> "Single"
                    isMaybe -> "Maybe"
                    else -> "Observable"
                }
                throw IllegalStateException(
                        name
                                + " return type must be parameterized"
                                + " as "
                                + name
                                + "<Foo> or "
                                + name
                                + "<? extends Foo>"
                )
            }

            if (!NetworkUtil.isConnected(context)) {
                return errorNoConnection(rawType)
            }

            if (isObservable) {
                return (wrapped.adapt(call) as Observable<Response<*>>)
                        .map { response -> handleResponse(response) }
            }

            if (isSingle) {
                return (wrapped.adapt(call) as Single<Response<*>>)
                        .map { response -> handleResponse(response) }
            }

            if (isMaybe) {
                return (wrapped.adapt(call) as Maybe<Response<*>>)
                        .map { response -> handleResponse(response) }
            }

            if (isFlowable) {
                return (wrapped.adapt(call) as Flowable<Response<*>>)
                        .map { response -> handleResponse(response) }
            }

            return (wrapped.adapt(call) as Completable)
        }
    }

    /**
     * Handle token expired
     */
    private fun handleResponse(response: Response<*>): Any {
        return response
    }

    private fun errorNoConnection(rawType: Class<*>): Any {
        Log.d("Response", "ERROR")
        return when (rawType) {
            Flowable::class.java -> {
                Flowable.error<NoInternetException>(NoInternetException())
            }
            Single::class.java -> {
                Single.error<NoInternetException>(NoInternetException())
            }
            Maybe::class.java -> {
                Maybe.error<NoInternetException>(NoInternetException())
            }
            Completable::class.java -> {
                Completable.error(NoInternetException())
            }
            else -> {
                Observable.error<NoInternetException>(NoInternetException())
            }
        }
    }
}
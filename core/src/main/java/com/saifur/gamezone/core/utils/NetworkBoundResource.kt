package com.saifur.gamezone.core.utils

import android.util.Log
import com.saifur.gamezone.core.data.source.remote.config.ApiResponse
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> Flow<ApiResponse<RequestType>>,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
): Flow<Resource<ResultType>> = flow {
    emit(Resource.Loading()) // Emit Loading

    val localData = query().first() // Ambil data dari database pertama kali
    if (shouldFetch(localData)) {
        emit(Resource.Loading(localData)) // Emit loading dengan data cache
        try {
            fetch().collect { apiResponse ->
                when (apiResponse) {
                    is ApiResponse.Success -> {
                        saveFetchResult(apiResponse.data) // Simpan ke database
                        emitAll(query().map { Resource.Success(it) }) // Emit hasil dari database setelah update
                    }
                    is ApiResponse.Empty -> {
                        emitAll(query().map { Resource.Success(it) }) // Tidak ada data baru, tetap pakai data lama
                    }
                    is ApiResponse.Error -> {
                        emitAll(query().map { Resource.Error(apiResponse.errorMessage, it) }) // Emit error dengan data lama
                    }
                }
            }
        } catch (throwable: Throwable) {
            Log.e("NetworkBoundResource", throwable.toString())
            emitAll(query().map { Resource.Error(throwable.message ?: "Unknown error", it) })
        }
    } else {
        emitAll(query().map { Resource.Success(it) }) // Emit langsung dari database jika tidak perlu fetch
    }
}

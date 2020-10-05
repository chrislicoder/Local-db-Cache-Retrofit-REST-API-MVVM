package com.chrislicoder.foodrecipes_dbcache.util.network

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.chrislicoder.foodrecipes_dbcache.requests.responses.ApiResponse
import com.chrislicoder.foodrecipes_dbcache.requests.responses.ApiResponse.ApiSuccessResponse
import com.chrislicoder.foodrecipes_dbcache.util.AppExecutors

// CacheObject: Type for the Resource data. (database cache)
// RequestObject: Type for the API response. (network request)
abstract class NetworkBoundResource<CacheObject, RequestObject>(private val appExecutors: AppExecutors) {
    init {
        init()
    }

    private val results: MediatorLiveData<Resource<CacheObject>> = MediatorLiveData()

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract fun saveCallResult(@NonNull item: RequestObject)

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract fun shouldFetch(@Nullable data: CacheObject): Boolean

    // Called to get the cached data from the database.
    @NonNull
    @MainThread
    protected abstract fun loadFromDb(): LiveData<CacheObject>

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestObject>>

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    fun cachedObjectResults(): LiveData<Resource<CacheObject>> = results

    private fun init() {

        // update LiveData for loading status
        results.value = Resource.Loading(null)

        // observe LiveData source from local db
        val dbSource = loadFromDb()
        results.addSource(dbSource) { cacheObject ->
            results.removeSource(dbSource)
            if (shouldFetch(cacheObject)) {
                // get data from the network
                fetchFromNetwork(dbSource)
            } else {
                results.addSource(dbSource) {
                    setValue(Resource.Success(cacheObject))
                }
            }
        }
    }

    /**
     * 1) observe local db
     * 2) if <condition></condition> query the network
     * 3) stop observing the local db
     * 4) insert new data into local db
     * 5) begin observing local db again to see the refreshed data from network
     * @param dbSource
     */
    private fun fetchFromNetwork(dbSource: LiveData<CacheObject>) {
        Log.d(TAG, "fetchFromNetwork: called.")

        // update LiveData for loading status
        results.addSource(dbSource) {
            setValue(Resource.Loading(it))
        }

        val apiResponse = createCall()
        results.addSource(apiResponse) { requestObjectApiResponse ->
            results.removeSource(dbSource)
            results.removeSource(apiResponse)

            /*
                3 cases:
                   1) ApiSuccessResponse
                   2) ApiErrorResponse
                   3) ApiEmptyResponse
             */
            when (requestObjectApiResponse) {
                is ApiSuccessResponse -> {
                    Log.d(TAG, "onChanged: ApiSuccessResponse.")
                    appExecutors.diskIO().execute { // save the response to the local db
                        // save the response to the local db
                        processResponse(requestObjectApiResponse)?.let {
                            saveCallResult(it)
                        }
                        appExecutors.mainThread().execute {
                            results.addSource(loadFromDb()) {
                                setValue(Resource.Success(it))
                            }
                        }
                    }
                }
                is ApiResponse.ApiEmptyResponse -> {
                    Log.d(TAG, "onChanged: ApiEmptyResponse")
                    appExecutors.mainThread().execute {
                        results.addSource(loadFromDb()) {
                            setValue(Resource.Success(it))
                        }
                    }
                }
                is ApiResponse.ApiErrorResponse -> {
                    Log.d(TAG, "onChanged: ApiErrorResponse.")
                    results.addSource(dbSource) {
                        setValue(
                            Resource.Error(
                                requestObjectApiResponse.errorMessage,
                                it
                            )
                        )
                    }
                }
            }
        }
    }

    private fun processResponse(response: ApiSuccessResponse<RequestObject>?): RequestObject? {
        return response?.body
    }

    private fun setValue(newValue: Resource<CacheObject>) {
        results.takeIf { results.value != newValue }.apply { results.value = newValue }
    }

    companion object {
        const val TAG = "NetworkBoundResource"
    }
}

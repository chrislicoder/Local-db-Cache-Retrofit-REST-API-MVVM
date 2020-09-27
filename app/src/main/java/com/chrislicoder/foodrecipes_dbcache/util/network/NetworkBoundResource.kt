package com.chrislicoder.foodrecipes_dbcache.util.network

import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.chrislicoder.foodrecipes_dbcache.requests.responses.ApiResponse

// CacheObject: Type for the Resource data. (database cache)
// RequestObject: Type for the API response. (network request)
abstract class NetworkBoundResource<CacheObject, RequestObject> {
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
    protected abstract fun loadFromDb(): LiveData<CacheObject>?

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestObject>?>?

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    fun cachedObjectResults(): LiveData<Resource<CacheObject>> = results
}
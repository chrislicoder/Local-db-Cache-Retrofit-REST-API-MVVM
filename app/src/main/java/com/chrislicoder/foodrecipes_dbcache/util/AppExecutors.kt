package com.chrislicoder.foodrecipes_dbcache.util

import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class AppExecutors private constructor() {
    private val mNetworkIO =
            Executors.newScheduledThreadPool(3)

    fun networkIO(): ScheduledExecutorService {
        return mNetworkIO
    }

    private val mDiskIO: Executor = Executors.newSingleThreadExecutor()

    private val mMainThreadExecutor: Executor = MainThreadExecutor()


    fun diskIO(): Executor? {
        return mDiskIO
    }

    fun mainThread(): Executor? {
        return mMainThreadExecutor
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(@NonNull command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {
        val instance by lazy {
            AppExecutors()
        }
    }
}

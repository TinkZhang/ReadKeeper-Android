package app.tinks.readkeeper.basic

import android.app.Application
import android.content.Context
import android.util.Log
import timber.log.Timber

class ReadApplication : Application() {
    companion object {
        private lateinit var instance: ReadApplication

        fun getContext(): Context {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(object : Timber.Tree() {
                override fun isLoggable(tag: String?, priority: Int): Boolean {
                    if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                        return false
                    }
                    return true
                }

                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    if (!isLoggable(tag, priority)) {
                        return
                    }
                    super.log(priority, tag, message, t)
                }
            })
        }
    }
}

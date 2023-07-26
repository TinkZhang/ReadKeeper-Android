package app.tinks.readkeeper

//@HiltAndroidApp
//class ReadApplication : Application() {
//    var context: Context? = null
//    override fun onCreate() {
//        super.onCreate()
//        context = context
//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        } else {
//            Timber.plant(object : Timber.Tree() {
//                override fun isLoggable(tag: String?, priority: Int): Boolean {
//                    if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
//                        return false
//                    }
//                    return true
//                }
//
//                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
//                    if (!isLoggable(tag, priority)) {
//                        return
//                    }
//                    super.log(priority, tag, message, t)
//                }
//            })
//        }
//    }
//}

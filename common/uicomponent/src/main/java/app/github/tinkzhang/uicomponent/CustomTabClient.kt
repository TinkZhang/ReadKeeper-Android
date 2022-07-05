package app.github.tinkzhang.uicomponent

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.browser.customtabs.*
import androidx.browser.customtabs.CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION

class RkCustomTabClient(context: Context) : CustomTabsServiceConnection() {
    private var mCustomTabsSession: CustomTabsSession? = null
    private var mContext: Context?
    private var mDestinationUriOnAbort: Uri? = null
    private val mDestinationUriOnPageLoadFailed: Uri? = null
    private var mDestinationUriOnTimeout: Uri? = null

    init {
        mContext = context
        val packageName = getPackageName(context)
        if (packageName != null) {
            CustomTabsClient.bindCustomTabsService(context, getPackageName(context), this)
            Log.d("CustomTabClient", "Binding custom tab client")
        }
    }

    fun launchTabButKeepHistory(urlString: String) {
        val builder = intentBuilder
        val customTabsIntent = builder.build()
        customTabsIntent.intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        customTabsIntent.intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        launchTab(urlString, customTabsIntent)
    }

    fun launchTab(urlString: String) {
        val builder = intentBuilder
        val customTabsIntent = builder.build()
        customTabsIntent.intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        launchTab(urlString, customTabsIntent)
    }

    private fun launchTab(urlString: String, customTabsIntent: CustomTabsIntent) {
        if (mContext != null) {
            if (mCustomTabsSession != null) {
                customTabsIntent.launchUrl(mContext!!, Uri.parse(urlString))
                Log.d("CustomTabClient", "Launching custom tab client for $urlString")
            } else {
                Log.d("CustomTabClient", "Launching external browser for $urlString")
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(urlString)
                i.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                mContext!!.startActivity(i)
            }
        }
    }

    private val intentBuilder: CustomTabsIntent.Builder
        get() {
            val builder = CustomTabsIntent.Builder(mCustomTabsSession)
//            builder.setCloseButtonIcon(BitmapFactory.decodeResource(mContext!!.resources, R.drawable.ic_close_24dp_white))
//            builder.setStartAnimations(mContext!!, R.anim.enter_from_right, R.anim.exit_to_left)
//            builder.setExitAnimations(mContext!!, R.anim.enter_from_left, R.anim.exit_to_right)
            return builder
        }

    fun setDestinationUriOnAbort(destinationUriOnAbort: Uri?) {
        mDestinationUriOnAbort = destinationUriOnAbort
    }

    fun setDestinationUriOnTimeout(destinationUriOnTimeout: Uri?) {
        mDestinationUriOnTimeout = destinationUriOnTimeout
    }

    fun destroy() {
        if (mContext != null && mCustomTabsSession != null) {
            Log.d("CustomTabClient", "Unbinding custom tab client")
            mContext!!.unbindService(this)
        }
        mContext = null
        mCustomTabsSession = null
    }

    override fun onCustomTabsServiceConnected(name: ComponentName, client: CustomTabsClient) {
        client.warmup(0L)
        mCustomTabsSession = client.newSession(object : CustomTabsCallback() {
            override fun onNavigationEvent(navigationEvent: Int, extras: Bundle?) {
                if (mContext == null) {
                    return
                }
                when (navigationEvent) {
                    NAVIGATION_ABORTED -> if (mDestinationUriOnAbort != null) {
                        mContext!!.startActivity(Intent(Intent.ACTION_VIEW, mDestinationUriOnAbort))
                    }
                    NAVIGATION_FAILED -> if (mDestinationUriOnPageLoadFailed != null) {
                        mContext!!.startActivity(Intent(Intent.ACTION_VIEW, mDestinationUriOnPageLoadFailed))
                    }
                }
            }
        })
    }

    override fun onServiceDisconnected(componentName: ComponentName) {
        mCustomTabsSession = null
    }

    /**
     * Goes through all apps that handle VIEW intents and have a warmup service. Picks
     * the one chosen by the user if there is one, otherwise makes a best effort to return a
     * valid package name.
     *
     *
     * This is **not** threadsafe.
     *
     * @param context [Context] to use for accessing [PackageManager].
     * @return The package name recommended to use for connecting to custom tabs related components.
     */
    private fun getPackageName(context: Context): String? {
        if (sPackageNameToUse != null) return sPackageNameToUse
        val pm = context.packageManager
        val activityIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"))
        val defaultViewHandlerInfo = pm.resolveActivity(activityIntent, 0)
        var defaultViewHandlerPackageName: String? = null
        if (defaultViewHandlerInfo != null) {
            defaultViewHandlerPackageName = defaultViewHandlerInfo.activityInfo.packageName
        }
        val resolvedActivityList = pm.queryIntentActivities(activityIntent, 0)
        val packagesSupportingCustomTabs: MutableList<String> = ArrayList()
        for (info in resolvedActivityList) {
            val serviceIntent = Intent()
            serviceIntent.action = ACTION_CUSTOM_TABS_CONNECTION
            serviceIntent.setPackage(info.activityInfo.packageName)
            if (pm.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info.activityInfo.packageName)
            }
        }
        if (packagesSupportingCustomTabs.isEmpty()) {
            sPackageNameToUse = null
        } else if (packagesSupportingCustomTabs.size == 1) {
            sPackageNameToUse = packagesSupportingCustomTabs[0]
        } else if (!TextUtils.isEmpty(defaultViewHandlerPackageName)
            && !hasSpecializedHandlerIntents(context, activityIntent)
            && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName)) {
            sPackageNameToUse = defaultViewHandlerPackageName
        } else if (packagesSupportingCustomTabs.contains(STABLE_PACKAGE)) {
            sPackageNameToUse = STABLE_PACKAGE
        } else if (packagesSupportingCustomTabs.contains(BETA_PACKAGE)) {
            sPackageNameToUse = BETA_PACKAGE
        } else if (packagesSupportingCustomTabs.contains(DEV_PACKAGE)) {
            sPackageNameToUse = DEV_PACKAGE
        } else if (packagesSupportingCustomTabs.contains(LOCAL_PACKAGE)) {
            sPackageNameToUse = LOCAL_PACKAGE
        }
        return sPackageNameToUse
    }

    companion object {
        private const val TAG = "CustomTabsHelper"
        private const val STABLE_PACKAGE = "com.android.chrome"
        private const val BETA_PACKAGE = "com.chrome.beta"
        private const val DEV_PACKAGE = "com.chrome.dev"
        private const val LOCAL_PACKAGE = "com.google.android.apps.chrome"
        private var sPackageNameToUse: String? = null

        /**
         * Used to check whether there is a specialized handler for a given intent.
         *
         * @param intent The intent to check with.
         * @return Whether there is a specialized handler for the given intent.
         */
        private fun hasSpecializedHandlerIntents(context: Context, intent: Intent): Boolean {
            try {
                val pm = context.packageManager
                val handlers = pm.queryIntentActivities(
                    intent,
                    PackageManager.GET_RESOLVED_FILTER)
                if (handlers.size == 0) {
                    return false
                }
                for (resolveInfo in handlers) {
                    val filter = resolveInfo.filter ?: continue
                    if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue
                    if (resolveInfo.activityInfo == null) continue
                    return true
                }
            } catch (e: RuntimeException) {
                Log.e(TAG, "Runtime exception while getting specialized handlers")
            }
            return false
        }
    }
}
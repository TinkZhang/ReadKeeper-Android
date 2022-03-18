package com.github.readkeeper.instabug

import android.app.Application
import com.instabug.bug.BugReporting
import com.instabug.bug.invocation.Option
import com.instabug.library.Instabug
import com.instabug.library.invocation.InstabugInvocationEvent

object InstabugWrapper {
    var enabled: Boolean = true
    private var initialized: Boolean = false
    fun init(application: Application, token: String) {
        if (enabled) {
            Instabug.Builder(application, token)
                .setInvocationEvents(InstabugInvocationEvent.SHAKE, InstabugInvocationEvent.SCREENSHOT)
                .build()
            BugReporting.setOptions(Option.EMAIL_FIELD_OPTIONAL)
            BugReporting.setAttachmentTypesEnabled(false, false, false, false)
            initialized = true
        }
    }

    fun show() {
        if (initialized) {
            Instabug.show()
        }
    }
}
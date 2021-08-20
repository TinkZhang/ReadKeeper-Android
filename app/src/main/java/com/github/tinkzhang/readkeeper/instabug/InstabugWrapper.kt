package com.github.tinkzhang.readkeeper.instabug

import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.ReadApplication
import com.instabug.bug.BugReporting
import com.instabug.bug.invocation.Option
import com.instabug.library.Instabug
import com.instabug.library.invocation.InstabugInvocationEvent


object InstabugWrapper {
    var enabled: Boolean = true
    private var initialized: Boolean = false
    fun init(application: ReadApplication) {
        if (enabled) {
            val token = application.getString(R.string.instabug_token)
            Instabug.Builder(application, token)
                .setInvocationEvents(InstabugInvocationEvent.NONE)
                .build()
            BugReporting.setOptions(Option.EMAIL_FIELD_OPTIONAL)
            BugReporting.setAttachmentTypesEnabled(false, true, true, true)
            initialized = true
        }
    }

    fun show() {
        if (initialized) {
            Instabug.show()
        }
    }
}
package io.bluetrace.opentrace

import android.app.Activity
import java.util.*

fun Activity.setApplicationLanguage(newLanguage: String) {
    val activityRes = resources
    val activityConf = activityRes.configuration
    val newLocale = Locale(newLanguage)
    activityConf.setLocale(newLocale)
    activityRes.updateConfiguration(activityConf, activityRes.displayMetrics)

    val applicationRes = applicationContext.resources
    val applicationConf = applicationRes.configuration
    applicationConf.setLocale(newLocale)
    applicationRes.updateConfiguration(applicationConf,
        applicationRes.displayMetrics)
}
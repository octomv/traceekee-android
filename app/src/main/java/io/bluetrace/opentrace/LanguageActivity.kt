package io.bluetrace.opentrace

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

abstract class LanguageActivity : AppCompatActivity() {

    override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
//        context?.let {
//            setApplicationLanguage(Preference.getLang(context))
//        }
    }
}
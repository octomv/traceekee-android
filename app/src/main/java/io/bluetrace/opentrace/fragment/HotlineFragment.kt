package io.bluetrace.opentrace.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.bluetrace.opentrace.Preference
import io.bluetrace.opentrace.R
import kotlinx.android.synthetic.main.activity_plot.*

class HotlineFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_plot, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(webView) {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
        }

        webView.loadUrl("https://trace.hpa.gov.mv/hotline.html?lang=${Preference.getLang(webView.context)}")
    }
}




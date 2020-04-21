package io.bluetrace.opentrace.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import io.bluetrace.opentrace.Preference
import io.bluetrace.opentrace.R
import io.bluetrace.opentrace.reduceDragSensitivity
import kotlinx.android.synthetic.main.activity_plot.*
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = InfoPagerAdapter(view.context)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.stats)
                1 -> getString(R.string.updates)
                2 -> getString(R.string.feed)
                else -> getString(R.string.updates)
            }
        }.attach()

        viewPager.reduceDragSensitivity()

    }

    inner class InfoPagerAdapter(private val ctx: Context): FragmentStateAdapter(this) {

        override fun createFragment(position: Int): Fragment =
            InfoFragmentInner()
                .apply {
                    body = when (position) {
                        0 -> "https://trace.hpa.gov.mv/stats.html?lang=${Preference.getLang(ctx)}"
                        1 -> "https://trace.hpa.gov.mv/updates.html?lang=${Preference.getLang(ctx)}"
                        2 -> "https://trace.hpa.gov.mv/feed.html?lang=${Preference.getLang(ctx)}"
                        else -> "https://trace.hpa.gov.mv/feed.html?lang=${Preference.getLang(ctx)}"
                    }
                }

        override fun getItemCount(): Int = 3

    }

}

class InfoFragmentInner : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_plot, container, false)
    }

    var body: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(webView) {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
        }
    }

    override fun onResume() {
        super.onResume()
        webView.post {
            if(body?.startsWith("http", ignoreCase = true)==true){
                webView.loadUrl(body)
            } else {
                webView.loadData(body, "text/html", "utf-8")
            }
        }
    }

}




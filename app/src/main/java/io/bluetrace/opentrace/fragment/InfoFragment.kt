package io.bluetrace.opentrace.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import io.bluetrace.opentrace.R
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
        viewPager.adapter = InfoPagerAdapter()
        tabLayout.setupWithViewPager(viewPager)

    }

    inner class InfoPagerAdapter(): FragmentStatePagerAdapter(childFragmentManager) {

        override fun getItem(position: Int): Fragment =
            InfoFragmentInner()
                .apply {
                    body = when (position) {
                        0 -> "https://traceekee.netlify.app/updates.html"
                        1 -> "https://traceekee.netlify.app/stats.html"
                        2 -> "<a class='twitter-timeline' data-theme='light' href='https://twitter.com/HPA_MV?ref_src=twsrc%5Etfw'>Tweets by HPA_MV</a>" +
                                "<script async src='https://platform.twitter.com/widgets.js' charset='utf-8'></script>"
                        else -> "https://traceekee.netlify.app/stats.html"
                    }
                }

        override fun getCount(): Int = 3

        override fun getPageTitle(position: Int): CharSequence? =
            when (position) {
                0 -> getString(R.string.updates)
                1 -> getString(R.string.stats)
                2 -> getString(R.string.feed)
                else -> getString(R.string.updates)
            }

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




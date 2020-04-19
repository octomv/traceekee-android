package io.bluetrace.opentrace

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import io.bluetrace.opentrace.fragment.HomeFragment
import io.bluetrace.opentrace.fragment.HotlineFragment
import io.bluetrace.opentrace.fragment.InfoFragment
import io.bluetrace.opentrace.fragment.UploadPageFragment
import io.bluetrace.opentrace.logging.CentralLog
import kotlinx.android.synthetic.main.activity_main_new.*


class MainActivity : LanguageActivity() {

    private val TAG = "MainActivity"

    // navigation
    private var mNavigationLevel = 0
    var LAYOUT_MAIN_ID = 0
    private var selected = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_new)

        Utils.startBluetoothMonitoringService(this)

        LAYOUT_MAIN_ID = R.id.content

        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_home -> {

                        nav_view.itemTextColor =AppCompatResources.getColorStateList(nav_view.context, R.color.selector_bottombar_text_1)
                        nav_view.itemIconTintList = AppCompatResources.getColorStateList(nav_view.context, R.color.selector_bottombar_text_1)

                        if (selected != R.id.navigation_home) {
                            openFragment(
                                LAYOUT_MAIN_ID, HomeFragment(),
                                HomeFragment::class.java.name, 0
                            )
                        }
                        selected = R.id.navigation_home
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_upload -> {

                        nav_view.itemTextColor =AppCompatResources.getColorStateList(nav_view.context, R.color.selector_bottombar_text_2)
                        nav_view.itemIconTintList = AppCompatResources.getColorStateList(nav_view.context, R.color.selector_bottombar_text_2)

                        if (selected != R.id.navigation_upload) {
                            openFragment(
                                LAYOUT_MAIN_ID,
                                UploadPageFragment(),
                                UploadPageFragment::class.java.name,
                                0
                            )
                        }

                        selected = R.id.navigation_upload
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_help -> {

                        nav_view.itemTextColor =AppCompatResources.getColorStateList(nav_view.context, R.color.selector_bottombar_text_3)
                        nav_view.itemIconTintList = AppCompatResources.getColorStateList(nav_view.context, R.color.selector_bottombar_text_3)

                        if (selected != R.id.navigation_help) {
                            openFragment(
                                LAYOUT_MAIN_ID, InfoFragment(),
                                InfoFragment::class.java.name, 0
                            )
                        }

                        selected = R.id.navigation_help
                        return@OnNavigationItemSelectedListener true
                    }

                    R.id.navigation_hotline -> {

                        nav_view.itemTextColor =AppCompatResources.getColorStateList(nav_view.context, R.color.selector_bottombar_text_4)
                        nav_view.itemIconTintList = AppCompatResources.getColorStateList(nav_view.context, R.color.selector_bottombar_text_4)

                        if (selected != R.id.navigation_hotline) {
                            openFragment(
                                LAYOUT_MAIN_ID, HotlineFragment(),
                                HotlineFragment::class.java.name, 0
                            )
                        }

                        selected = R.id.navigation_hotline
                        return@OnNavigationItemSelectedListener true
                    }


                    else -> {
                        Toast.makeText(this, "To be implemented", Toast.LENGTH_LONG).show()
                    }
                }
                false
            }

        nav_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        goToHome()

        getFCMToken()
    }


    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener { task ->
                if (!task.isSuccessful()) {
                    CentralLog.w(TAG, "failed to get fcm token ${task.exception}")
                    return@addOnCompleteListener
                } else {
                    // Get new Instance ID token
                    val token = task.result?.token
                    // Log and toast
                    CentralLog.d(TAG, "FCM token: $token")
                }
            }


    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    fun goToHome() {
        nav_view.selectedItemId = R.id.navigation_home
    }

    fun openFragment(
        containerViewId: Int,
        fragment: Fragment,
        tag: String,
        title: Int
    ) {
        try { // pop all fragments
            supportFragmentManager.popBackStackImmediate(
                LAYOUT_MAIN_ID,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            mNavigationLevel = 0
            val transaction =
                supportFragmentManager.beginTransaction()
            transaction.replace(containerViewId, fragment, tag)
            transaction.commit()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}

package io.bluetrace.opentrace.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import io.bluetrace.opentrace.R
import kotlinx.android.synthetic.main.card_ob_1.*
import kotlinx.android.synthetic.main.card_ob_2.*
import kotlinx.android.synthetic.main.main_activity_onboarding.*

class PreOnboardingActivity : FragmentActivity() {

    private var curr_state: Int = 0
    private var hero_images = listOf(
        Pair(1, R.raw.ob_intro),
        Pair(0, R.drawable.mohir_onboarding_hiw)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_onboarding)
        setViewState(0)
    }


    //type 0 = img, 1=lottie
    private fun setHero(type: Int, res: Int) =
        when(type) {
            1 -> with(on_boarding_sub_banner) {
                    setAnimation(res)
                    if(!isAnimating)
                        playAnimation()
                }

            else -> {
                on_boarding_sub_banner.setImageDrawable(
                    AppCompatResources.getDrawable(
                        on_boarding_sub_banner.context,
                        res
                    )
                )
            }
        }


    private fun setViewState(state: Int) {
        curr_state = state
        when (state) {
            1 -> {
                //default and 0
                showViewOnly(ob_content2)
                hero_images.getOrNull(state)?.let { setHero(it.first, it.second) }
                btn_onboardingStart.setText(R.string.begin)
                btn_onboardingStart.setOnClickListener {
                        val intent = Intent(this, OnboardingActivity::class.java)
                        startActivity(intent)
                }
            }

            else -> {
                //default and 0
                showViewOnly(ob_content1)
                hero_images.getOrNull(state)?.let { setHero(it.first, it.second) }
                btn_onboardingStart.setText(R.string.continue_)
                btn_onboardingStart.setOnClickListener {
                    setViewState(1)
                }
            }
        }

    }

    private fun showViewOnly(view: View){
        listOf(ob_content1, ob_content2).forEach { it.isVisible = (it==view) }
    }

    override fun onBackPressed() {
        if(curr_state==0)
            super.onBackPressed()
        else {
            curr_state--
            setViewState(curr_state.coerceAtLeast(0))
        }
    }

}

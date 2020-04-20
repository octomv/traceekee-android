package io.bluetrace.opentrace.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import io.bluetrace.opentrace.LanguageActivity
import io.bluetrace.opentrace.Preference
import io.bluetrace.opentrace.R
import kotlinx.android.synthetic.main.card_ob_1.*
import kotlinx.android.synthetic.main.card_ob_2.*
import kotlinx.android.synthetic.main.card_ob_lang.*
import kotlinx.android.synthetic.main.main_activity_onboarding.*

class PreOnboardingActivity : LanguageActivity() {

    private var curr_state: Int = 0
    private var hero_images = listOf(
        Pair(0, R.drawable.mohir_consent_2),
        Pair(1, R.raw.ob_intro),
        Pair(0, R.drawable.mohir_onboarding_hiw)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_onboarding)
        initLangFlipper()
        setViewState(0)
    }

    private fun initLangFlipper() {
        when(Preference.getLang(this)) {
            "en" -> {
                lang_btn_en.setBackgroundResource(R.drawable.green_pill_rounded)
            }

            else -> {
                lang_btn_dv.setBackgroundResource(R.drawable.green_pill_rounded)
            }
        }

        lang_btn_dv.setOnClickListener { setLang("dv") }
        lang_btn_en.setOnClickListener { setLang("en") }
    }

    private fun setLang(lang: String) {
        Preference.setLang(this, lang)
        recreate()
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
            0 -> {

                showViewOnly(ob_lang)
                tv_title.isVisible = false

                btn_onboardingStart.setText(R.string.continue_)
                hero_images.getOrNull(state)?.let { setHero(it.first, it.second) }
                btn_onboardingStart.setOnClickListener {
                  setViewState(1)
                }

            }

            1 -> {
                showViewOnly(ob_content1)
                tv_title.isVisible = true

                hero_images.getOrNull(state)?.let { setHero(it.first, it.second) }
                btn_onboardingStart.setText(R.string.continue_)
                btn_onboardingStart.setOnClickListener {
                    setViewState(2)
                }
            }

            2 -> {
                //default and 0
                showViewOnly(ob_content2)
                tv_title.isVisible = true

                btn_onboardingStart.setText(R.string.begin)
                hero_images.getOrNull(state)?.let { setHero(it.first, it.second) }
                btn_onboardingStart.setOnClickListener {
                        val intent = Intent(this, OnboardingActivity::class.java)
                        startActivity(intent)
                }
            }


        }

    }

    private fun showViewOnly(view: View){
        listOf(ob_content1, ob_content2, ob_lang).forEach { it.isVisible = (it==view) }
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

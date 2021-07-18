package com.kodluyoruz.onboardingflow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {

   private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter
   private lateinit var indicatorsContainer : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)
    }

    //description
    private fun setOnboardingItems(){
        onboardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
                    onboardingImage = R.drawable.plates,
                    title = "Health Tips / Exercise",
                    description = "Discover tips and advice to help you to help maintain transform and main your health."
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.training,
                    title = "Health Tips / Training",
                    description = "Discover tips and advice to help you to help maintain transform and main your health."
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.yoga,
                    title = "Health Tips / Yoga",
                    description = "Start yoga practice, Improve your health"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.diet,
                    title = "Diet Tips / Advice",
                    description = "Find out basic of health diet and good nutrition, Start eating well and keep a balanced diet."
                )
            )
        )
        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        onboardingViewPager.adapter= onboardingItemsAdapter
        onboardingViewPager.registerOnPageChangeCallback(object  :
        ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        //nextbutton control
        findViewById<ImageView>(R.id.imageNext).setOnClickListener{
            if(onboardingViewPager.currentItem + 1 < onboardingItemsAdapter.itemCount){
                onboardingViewPager.currentItem +=1
            }else{
                navigateToHomeActivity()
            }
        }
        //skip click
        findViewById<TextView>(R.id.textSkip).setOnClickListener {
            navigateToHomeActivity()
        }
        //getstarted button click
        findViewById<MaterialButton>(R.id.buttonGetStarted).setOnClickListener {
            navigateToHomeActivity()
        }
    }

    //intent
    private fun navigateToHomeActivity(){
        startActivity(Intent( applicationContext,HomeActivity::class.java))
        finish()
    }
    private  fun setupIndicators(){
        indicatorsContainer = findViewById(R.id.indicatorContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for(i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let{
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactivate_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }
    private  fun setCurrentIndicator(position: Int){
        val childCount = indicatorsContainer.childCount
        for ( i in 0 until childCount){
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if( i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_activate_background
                    )
                )

            }else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactivate_background
                    )
                )
            }
        }
    }
}


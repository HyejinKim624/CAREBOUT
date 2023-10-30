package com.example.carebout.base

import android.app.Activity
import android.content.Intent
import com.example.carebout.databinding.BottomTapBinding
import com.example.carebout.view.calendar.CalendarActivity
import com.example.carebout.view.community.CommunityActivity
import com.example.carebout.view.home.HomeActivity
import com.example.carebout.view.medical.MedicalActivity

fun bottomTabClick(btmBinding: BottomTapBinding, nowActivity: Activity){
    btmBinding.goToHome.setOnClickListener {
        nowActivity.startActivity(
            Intent(
                nowActivity, HomeActivity::class.java
            )
        )
        nowActivity.finish()
    }
    btmBinding.goToCalendar.setOnClickListener {
        nowActivity.startActivity(
            Intent(
                nowActivity, CalendarActivity::class.java
            )
        )
        nowActivity.finish()
    }
    btmBinding.goToCommunity.setOnClickListener {
        nowActivity.startActivity(
            Intent(
                nowActivity, CommunityActivity::class.java
            )
        )
        nowActivity.finish()
    }
    btmBinding.goToMedical.setOnClickListener {
        nowActivity.startActivity(
            Intent(
                nowActivity, MedicalActivity::class.java
            )
        )
        nowActivity.finish()
    }
}
package com.example.carebout.view.medical

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import com.example.carebout.R
import com.google.android.material.tabs.TabLayout


class MedicalNoteTab : Fragment() {

    var tab1 = Tab1()
    var tab2 = Tab2()
    var tab3 = Tab3()
    var dailycare = Dailycare()
    val currentWeight = CurrentWeight()
    val mediing = Mediing()

    fun subUnselectBorn() {
        dailycare.unSelectBorn()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val medicalNoteTabView: View = inflater.inflate(R.layout.medical_note_tab, container, false)

        var tabs = medicalNoteTabView.findViewById<TabLayout>(R.id.tabs)

        var selected: Fragment? = tab3
        childFragmentManager.beginTransaction().replace(R.id.frame1, selected!!).commit()
        childFragmentManager.beginTransaction().replace(R.id.dailycareFrame, dailycare!!).commit()
        childFragmentManager.beginTransaction().replace(R.id.currentWeight, currentWeight!!).commit()
        childFragmentManager.beginTransaction().replace(R.id.mediing, mediing!!).commit()

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position

                if (position == 0) {
                    selected = tab3
                } else if (position == 1) {
                    selected = tab2
                } else if (position == 2) {
                    selected = tab1
                }
                childFragmentManager.beginTransaction().replace(R.id.frame1, selected!!).commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return medicalNoteTabView
    }
}
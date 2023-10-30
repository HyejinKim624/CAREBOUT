package com.example.carebout.view.medical

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.carebout.R
import com.example.carebout.base.bottomTabClick
import com.example.carebout.databinding.ActivityMedicalBinding
import com.example.carebout.view.medical.Clinic.ClinicReadActivity
import com.example.carebout.view.medical.Clinic.ClinicWriteActivity
import com.example.carebout.view.medical.Inoc.InoculationReadActivity
import com.example.carebout.view.medical.Inoc.InoculationWriteActivity
import com.example.carebout.view.medical.Medicine.MedicineReadActivity
import com.example.carebout.view.medical.Medicine.MedicineWriteActivity
import com.example.carebout.view.medical.Todo.TodoReadActivity
import com.example.carebout.view.medical.Todo.TodoWriteActivity
import com.example.carebout.view.medical.db.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class MedicalActivity : AppCompatActivity() {

    var medicalNoteTab = MedicalNoteTab()

    private var isFabOpen = false
    lateinit var binding: ActivityMedicalBinding
    private lateinit var db: AppDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicalBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setContentView(R.layout.activity_medical)
//        val view = binding.root
//        setContentView(view)

        db = AppDatabase.getInstance(this)!!

//        var tab: TabLayout = findViewById(R.id.mainTab)

        var selected: Fragment? = medicalNoteTab

        supportFragmentManager.beginTransaction().replace(R.id.mainFrame, selected!!).commit()

//        tab.addOnTabSelectedListener(object : OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                val position = tab.position
//
//                if (position == 0) {
//                    selected = medicalNoteTab
//                } else if (position == 1) {
//                    selected = medicalNoteTab
//                } else if (position == 2) {
//                    medicalNoteTab.subUnselectBorn()
//                    selected = medicalNoteTab
//                } else if (position == 3) {
//                    selected = medicalNoteTab
//                }
//                supportFragmentManager.beginTransaction().replace(R.id.mainFrame, selected!!).commit()
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {}
//            override fun onTabReselected(tab: TabLayout.Tab) {}
//        })


        val todoListbtn: Button = findViewById(R.id.todoListButton)
        val mediListbtn: Button = findViewById(R.id.mediListButton)
        val clinicListbtn: Button = findViewById(R.id.clinicListButton)
        val inocListbtn: Button = findViewById(R.id.inocListButton)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        val backB: FrameLayout = findViewById(R.id.popup_menu_container)

        fab.setOnClickListener{
            toggleFab()
        }

        backB.setOnClickListener{
            toggleFab()
        }

        todoListbtn.setOnClickListener{
            val intent = Intent(this, TodoReadActivity::class.java)
            startActivity(intent)
        }

        inocListbtn.setOnClickListener{
            val intent = Intent(this, InoculationReadActivity::class.java)
            startActivity(intent)
        }

        mediListbtn.setOnClickListener{
            val intent = Intent(this, MedicineReadActivity::class.java)
            startActivity(intent)
        }

        clinicListbtn.setOnClickListener{
            val intent = Intent(this, ClinicReadActivity::class.java)
            startActivity(intent)
        }
    
        // 하단탭 클릭시 intent를 하기 위한 함수
        bottomTabClick(binding.bottomTapBarOuter, this)
    }

    private fun toggleFab() {
        val rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward)
        val rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        if (isFabOpen) {
            fab.startAnimation(rotateBackward)
        } else {
            fab.startAnimation(rotateForward)
        }

        isFabOpen = !isFabOpen

        togglePopupMenu()
    }

    private fun togglePopupMenu() {
        val popupMenuContainer: FrameLayout = findViewById(R.id.popup_menu_container)
        val popupMenu: LinearLayout = findViewById(R.id.popup_menu)


        //val popupMenu = binding.popupMenu
        //val popupMenuContainer = binding.popupMenuContainer

        if (isFabOpen) {
            val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            popupMenu.startAnimation(fadeIn)
            popupMenu.visibility = View.VISIBLE

            val fadeInBackground = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            popupMenuContainer.startAnimation(fadeInBackground)
            popupMenuContainer.visibility = View.VISIBLE
        } else {
            val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
            popupMenu.startAnimation(fadeOut)
            popupMenu.visibility = View.GONE

            val fadeOutBackground = AnimationUtils.loadAnimation(this, R.anim.fade_out)
            popupMenuContainer.startAnimation(fadeOutBackground)
            popupMenuContainer.visibility = View.GONE
        }
    }

    fun onMenuItemClick(view: View) {
        when (view.id) {
            R.id.menu_item_1 -> {
                val intent = Intent(this, ClinicWriteActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_item_2 -> {
                val intent = Intent(this, MedicineWriteActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_item_3 -> {
                val intent = Intent(this, InoculationWriteActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_item_4 -> {
                val intent = Intent(this, TodoWriteActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
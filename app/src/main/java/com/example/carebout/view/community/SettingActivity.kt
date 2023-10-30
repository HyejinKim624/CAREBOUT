package com.example.carebout.view.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carebout.R
import com.example.carebout.databinding.ActivityAddBinding
import com.example.carebout.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_setting)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
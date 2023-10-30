package com.example.carebout.view.community

import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carebout.R
import com.example.carebout.databinding.ActivityAddBinding
import com.example.carebout.view.community.CommunityActivity
import com.example.carebout.view.community.DBHelper
import com.example.carebout.view.community.OneFragment

class AddActivity: AppCompatActivity() {
    lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val userEnteredText = binding.storyTitle.text.toString()
        val fragment = OneFragment()
        val args = Bundle()
        args.putString("userEnteredText", userEnteredText) // 사용자가 입력한 텍스트를 Bundle에 추가
        fragment.arguments = args
    }

    override fun onCreateOptionsMenu (menu: Menu?): Boolean {
        menuInflater.inflate (R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected (item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> { // 뒤로가기 버튼을 누를 때
            // 이 부분 스택에 저장돼서 우리가 쓸 필요 없는 코드라고 해서 주석처리 해 놨어요!
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
            finish()
            true
        }

        R.id.menu_add_save -> {
            val inputData = binding.addEditView.text.toString()
            val db = DBHelper (this).writableDatabase
            db.execSQL ("insert into TODO_TB (todo) values (?)",
                arrayOf<String>(inputData))
            db.close()

            val intent = intent.putExtra("result", inputData)
            setResult(Activity.RESULT_OK, intent)
            finish()
            true
        }
        else -> true
    }
}
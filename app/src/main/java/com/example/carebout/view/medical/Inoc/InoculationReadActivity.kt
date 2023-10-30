package com.example.carebout.view.medical.Inoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carebout.R
import com.example.carebout.databinding.ActivityInoculationReadBinding
import com.example.carebout.view.medical.db.AppDatabase
import com.example.carebout.view.medical.db.Inoculation
import com.example.carebout.view.medical.db.InoculationDao
import com.google.android.material.floatingactionbutton.FloatingActionButton

class InoculationReadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInoculationReadBinding

    private lateinit var db: AppDatabase
    private lateinit var inocDao: InoculationDao
    private var inocList: ArrayList<Inoculation> = ArrayList<Inoculation>()
    private lateinit var adapter: InoculationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInoculationReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //db 인스턴스를 가져오고 db작업을 할 수 있는 dao를 가져옵니다.
        db = AppDatabase.getInstance(this)!!
        inocDao = db.getInocDao()

        val insertBtn: FloatingActionButton = findViewById(R.id.insert_btn)
        insertBtn.setOnClickListener {
            val intent: Intent = Intent(this, InoculationWriteActivity::class.java)

            activityResult.launch(intent)
        }

        //RecyclerView 설정
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //UserAdapter 초기화
        adapter = InoculationAdapter(this)

        //Adapter 적용
        recyclerView.adapter = adapter


        //조회
        getInocList()


        val tag1 = binding.toggle1
        val tag2 = binding.toggle2

        tag1.setOnCheckedChangeListener { _, isChecked ->
            getInocList()
            if (isChecked) {
                if (tag2.isChecked) {
                    tag2.isChecked = false
                }
                Log.i("check", "true")
                getInocTag1List()
            }
        }

        tag2.setOnCheckedChangeListener { _, isChecked ->
            getInocList()
            if (isChecked) {
                if (tag1.isChecked) {
                    tag1.isChecked = false
                }
                Log.i("check", "true")
                getInocTag2List()
            }
        }

//        tag1.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                if (tag2.isChecked) {
//                    tag2.isChecked = false
//                    Log.i("check", "true")
//                    getInocTag1List()
//                }
//            }else {
//                getInocList()
//            }
//        }
//
//        tag2.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                if (tag1.isChecked) {
//                    tag1.isChecked = false
//                    Log.i("check", "true")
//                    getInocTag2List()
//                }
//            }else {
//                getInocList()
//            }
//        }




    }

    //액티비티가 백그라운드에 있는데 호출되면 실행 /수정화면에서 호출시 실행
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        //사용자 조회
        getInocList()
    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            //들어온 값이 OK라면
            //리스트 조회
            getInocList()
        }
    }

    //리스트 조회
    private fun getInocList() {

        val inocList: ArrayList<Inoculation> = db?.getInocDao()!!.getInocDateAsc() as ArrayList<Inoculation>
            //.getInoculationAll() as ArrayList<Inoculation>

        if (inocList.isNotEmpty()) {
            //데이터 적용
            adapter.setInoculationList(inocList)

        } else {

        }
    }

    private fun getInocTag1List() {

        val inocTag1List: ArrayList<Inoculation> = db?.getInocDao()!!.getInocWithTag1() as ArrayList<Inoculation>

        if (inocTag1List.isNotEmpty()) {
            //데이터 적용
            adapter.setInoculationList(inocTag1List)

        } else {
            adapter.setInoculationList(ArrayList())
        }
    }

    private fun getInocTag2List() {

        val inocTag2List: ArrayList<Inoculation> = db?.getInocDao()!!.getInocWithTag2() as ArrayList<Inoculation>

        if (inocTag2List.isNotEmpty()) {
            //데이터 적용
            adapter.setInoculationList(inocTag2List)

        } else {
            adapter.setInoculationList(ArrayList())
        }
    }
}
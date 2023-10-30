package com.example.carebout.view.medical.Clinic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carebout.R
import com.example.carebout.databinding.ActivityClinicReadBinding
import com.example.carebout.view.medical.db.AppDatabase
import com.example.carebout.view.medical.db.Clinic
import com.example.carebout.view.medical.db.ClinicDao
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ClinicReadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClinicReadBinding

    private lateinit var db: AppDatabase
    private lateinit var clinicDao: ClinicDao
    private var clinicList: ArrayList<Clinic> = ArrayList<Clinic>()
    private lateinit var adapter: ClinicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClinicReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //db 인스턴스를 가져오고 db작업을 할 수 있는 dao를 가져옵니다.
        db = AppDatabase.getInstance(this)!!
        clinicDao = db.getClinicDao()

        val insertBtn: FloatingActionButton = findViewById(R.id.insert_btn)
        insertBtn.setOnClickListener {
            val intent: Intent = Intent(this, ClinicWriteActivity::class.java)

            activityResult.launch(intent)
        }

        //RecyclerView 설정
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //UserAdapter 초기화
        adapter = ClinicAdapter(this)

        //Adapter 적용
        recyclerView.adapter = adapter


        //조회
        getClinicList()

    }

    //액티비티가 백그라운드에 있는데 호출되면 실행 /수정화면에서 호출시 실행
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        //사용자 조회
        getClinicList()
    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            //들어온 값이 OK라면
            //리스트 조회
            getClinicList()
        }
    }

    //리스트 조회
    private fun getClinicList() {

        val clinicList: ArrayList<Clinic> = db?.getClinicDao()!!.getClinicAll() as ArrayList<Clinic>

        if (clinicList.isNotEmpty()) {
            //데이터 적용
            adapter.setClinicList(clinicList)

        } else {

        }
    }


}

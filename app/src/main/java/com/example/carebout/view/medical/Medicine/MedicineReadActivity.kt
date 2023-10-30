package com.example.carebout.view.medical.Medicine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carebout.R
import com.example.carebout.databinding.ActivityMedicineReadBinding
import com.example.carebout.view.medical.db.AppDatabase
import com.example.carebout.view.medical.db.Medicine
import com.example.carebout.view.medical.db.MedicineDao
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MedicineReadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMedicineReadBinding

    private lateinit var db: AppDatabase
    private lateinit var medicineDao: MedicineDao
    private var mediList: ArrayList<Medicine> = ArrayList<Medicine>()
    private lateinit var adapter: MedicineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //db 인스턴스를 가져오고 db작업을 할 수 있는 dao를 가져옵니다.
        db = AppDatabase.getInstance(this)!!
        medicineDao = db.getMedicineDao()

        val insertBtn: FloatingActionButton = findViewById(R.id.insert_btn)
        insertBtn.setOnClickListener {
            val intent: Intent = Intent(this, MedicineWriteActivity::class.java)

            activityResult.launch(intent)
        }

        //RecyclerView 설정
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //UserAdapter 초기화
        adapter = MedicineAdapter(this)

        //Adapter 적용
        recyclerView.adapter = adapter

        //조회
        getMedicineList()

        val checkTag = binding.toggle

        checkTag.setOnCheckedChangeListener { _, isChecked ->
            getMedicineList()
            if (isChecked) {
                Log.i("check", "true")
                getMediCheckList()
            }
        }

        }

        //액티비티가 백그라운드에 있는데 호출되면 실행 /수정화면에서 호출시 실행
        override fun onNewIntent(intent: Intent?) {
            super.onNewIntent(intent)

            //사용자 조회
            getMedicineList()
        }

        private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                //들어온 값이 OK라면
                //리스트 조회
                getMedicineList()
            }
        }

        //리스트 조회
        private fun getMedicineList() {

            val mediList: ArrayList<Medicine> = db?.getMedicineDao()!!.getMediAll() as ArrayList<Medicine>

            if (mediList.isNotEmpty()) {
                //데이터 적용
                adapter.setMediList(mediList)

                //val position: Int = todoList.size -1

                //Toast.makeText(this, "저장 정보 :" + todoList.get(position).title, Toast.LENGTH_SHORT).show()
            } else {

            }
        }

        private fun getMediCheckList() {

            val mediCheckList: ArrayList<Medicine> = db?.getMedicineDao()!!.getMediWithCheck() as ArrayList<Medicine>

            if (mediCheckList.isNotEmpty()) {
                //데이터 적용
                adapter.setMediList(mediCheckList)

            } else {
                adapter.setMediList(ArrayList())
            }
        }

}
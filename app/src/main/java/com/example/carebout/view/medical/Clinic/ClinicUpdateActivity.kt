package com.example.carebout.view.medical.Clinic

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.carebout.R
import com.example.carebout.databinding.ActivityClinicUpdateBinding
import com.example.carebout.view.medical.db.AppDatabase
import com.example.carebout.view.medical.db.Clinic
import com.example.carebout.view.medical.db.ClinicDao
import java.text.SimpleDateFormat
import java.util.Locale

class ClinicUpdateActivity : AppCompatActivity() {

    lateinit var binding: ActivityClinicUpdateBinding
    lateinit var db: AppDatabase
    lateinit var clinicDao: ClinicDao
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClinicUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(applicationContext)!!
        clinicDao = db.getClinicDao()

        val editTextList: EditText = findViewById(R.id.editTextList)
        val editTextDate: EditText = findViewById(R.id.editTextDate)
        val editTextH: EditText = findViewById(R.id.editTextH)
        val editTextMultiLine: TextView = findViewById(R.id.editTextMultiLine)

        val updateBtn: Button = findViewById(R.id.updateBtn)
        val deleteBtn: Button = findViewById(R.id.deleteBtn)

        // 수정 페이지로 전달된 아이템 정보를 가져옴
        val clinicId = intent.getIntExtra("clinicId", -1)
        id = clinicId
        Log.i("id", clinicId.toString())
        if (clinicId != -1) {
            // clinicId를 사용하여 데이터베이스에서 해당 아이템 정보를 가져와서 수정 페이지에서 사용할 수 있음
            // 수정 기능을 구현하는 코드 추가
            //넘어온 데이터 변수에 담기
            var uTag: String? = intent.getStringExtra("uTag")
            var uDate: String? = intent.getStringExtra("uDate")
            var uHospital: String? = intent.getStringExtra("uHospital")
            var uEtc: String? = intent.getStringExtra("uEtc")

            //화면에 값 적용
            editTextList.setText(uTag)
            editTextDate.setText(uDate)
            editTextH.setText(uHospital)
            editTextMultiLine.setText(uEtc)

            Log.i("in", uTag.toString())
        }

        updateBtn.setOnClickListener{
            updateClinic()
        }

        deleteBtn.setOnClickListener {
            deletClinic()
        }

        // 숫자 입력 시 대시 "-" 자동 추가
        setupDateEditText(binding.editTextDate)

    }

    private fun updateClinic() {

        val clinicTag = binding.editTextList.text.toString()
        val clinicDate = binding.editTextDate.text.toString() // 할일 제목
        val clinicH = binding.editTextH.text.toString()
        val clinicEtc = binding.editTextMultiLine.text.toString()

        // Date validation
        if (!isValidDate(clinicDate)) {
            Toast.makeText(
                this,
                "유효하지 않은 날짜 형식입니다. 항목을 다시 확인해주세요.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // 현재 날짜를 가져옴
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(System.currentTimeMillis())

        // clinicDate와 currentDate 비교
        if (clinicDate > currentDate) {
            Toast.makeText(
                this,
                "검진일에 미래 날짜는 입력할 수 없습니다.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val Clinic = Clinic(id, clinicTag, clinicDate, clinicH, clinicEtc)

        if (clinicTag.isBlank() || clinicDate.isBlank()) {
            Toast.makeText(this, "항목을 채워주세요", Toast.LENGTH_SHORT).show()
        } else {
            Thread {
                clinicDao.updateClinic(Clinic)
                Log.i("id", Clinic.toString())
                runOnUiThread {
                    Toast.makeText(
                        this, "수정되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    moveToAnotherPage()
                }
            }.start()
        }
    }

    private fun deletClinic() {
        Thread {
            val clinicToDelete = clinicDao.getClinicById(id)
            if (clinicToDelete != null) {
                clinicDao.deleteClinic(clinicToDelete)
                runOnUiThread {
                    Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    moveToAnotherPage()
                }
            }
        }.start()
    }

    private fun moveToAnotherPage() {
        val intent = Intent(applicationContext, ClinicReadActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Date validation function
    private fun isValidDate(dateString: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateFormat.isLenient = false
        return try {
            dateFormat.parse(dateString)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun setupDateEditText(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            @SuppressLint("SuspiciousIndentation")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check if the input exceeds 10 characters and remove extra characters
                if (s?.length ?: 0 > 10) {
                    val truncatedText = s?.subSequence(0, 10)
                    editText.setText(truncatedText)
                    editText.setSelection(10)
                    return
                }

                // Add dashes to the date input when necessary
                if (count == 1 && (start == 4 || start == 7)) {
                    s?.let {
                        val updatedText = StringBuilder(it)
                        //  if (start == 4|| start == 7) {
                        updatedText.insert(start, '-')
                        //  }
//                        else {
//                            updatedText.insert(start - 1, '-')
//                        }
                        editText.setText(updatedText)
                        editText.setSelection(updatedText.length) // 커서를 항상 마지막으로 이동
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
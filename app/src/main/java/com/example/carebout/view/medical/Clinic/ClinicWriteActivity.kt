package com.example.carebout.view.medical.Clinic

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.carebout.R
import com.example.carebout.databinding.ActivityClinicWriteBinding
import com.example.carebout.view.medical.db.AppDatabase
import com.example.carebout.view.medical.db.Clinic
import com.example.carebout.view.medical.db.ClinicDao
import java.text.SimpleDateFormat
import java.util.Locale

class ClinicWriteActivity : AppCompatActivity() {

    lateinit var binding: ActivityClinicWriteBinding
    lateinit var db: AppDatabase
    lateinit var clinicDao: ClinicDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClinicWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(applicationContext)!!
        clinicDao = db.getClinicDao()

        val editTextList: EditText = findViewById(R.id.editTextList)
        val editTextDate: EditText = findViewById(R.id.editTextDate)
        val editTextH: EditText = findViewById(R.id.editTextH)
        val editTextMultiLine: TextView = findViewById(R.id.editTextMultiLine)
        val btn1: Button = findViewById(R.id.button)

        val NowTime = System.currentTimeMillis()
        val DF = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)

        val result = DF.format(NowTime)
        editTextDate.setText(result)

        btn1.setOnClickListener {
            insertClinic()
        }
        // 숫자 입력 시 대시 "-" 자동 추가
        setupDateEditText(binding.editTextDate)
    }

    private fun insertClinic() {


        val clinicTag = binding.editTextList.text.toString()
        val clinicDate = binding.editTextDate.text.toString()
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

        val Clinic = Clinic(null, clinicTag, clinicDate, clinicH, clinicEtc)

        if (clinicTag.isBlank() || clinicDate.isBlank()) {
            Toast.makeText(this, "항목을 채워주세요", Toast.LENGTH_SHORT).show()
        } else {
            Thread {
                clinicDao.insertClinic(Clinic)
                runOnUiThread {
                    Toast.makeText(
                        this, "추가되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    moveToAnotherPage()
                }
            }.start()
        }

    }

    private fun moveToAnotherPage() {
        val intent = Intent(this, ClinicReadActivity::class.java)
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
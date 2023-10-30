package com.example.carebout.view.medical.Medicine

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.carebout.R
import com.example.carebout.databinding.ActivityMedicineWriteBinding
import com.example.carebout.view.medical.db.AppDatabase
import com.example.carebout.view.medical.db.Medicine
import com.example.carebout.view.medical.db.MedicineDao
import java.text.SimpleDateFormat
import java.util.Locale

class MedicineWriteActivity : AppCompatActivity() {

    lateinit var binding: ActivityMedicineWriteBinding
    lateinit var db: AppDatabase
    lateinit var medicineDao: MedicineDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMedicineWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(applicationContext)!!
        medicineDao = db.getMedicineDao()

        val editTextM: EditText = findViewById(R.id.editTextM)
        val editTextStartD: EditText = findViewById(R.id.editTextStartD)
        val editTextEndD: EditText = findViewById(R.id.editTextEndD)
        val checkBox: CheckBox = findViewById(R.id.checkBox)
        val editTextMultiLine: TextView = findViewById(R.id.editTextMultiLine)
        val btn1: Button = findViewById(R.id.button)

        val NowTime = System.currentTimeMillis()
        val DF = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)

        val result = DF.format(NowTime)

        editTextStartD.setText(result)

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.i("check", "true")
                editTextEndD.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
                editTextEndD.isEnabled = false
                editTextEndD.isFocusable = false
            } else {
                editTextEndD.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
                editTextEndD.isEnabled = true
                editTextEndD.isFocusableInTouchMode = true
                editTextEndD.isFocusable = true
            }
        }

        btn1.setOnClickListener {
            insertMedi()
        }
        // 숫자 입력 시 대시 "-" 자동 추가
        setupDateEditText(binding.editTextStartD)
        setupDateEditText(binding.editTextEndD)
        //editTextStartD.addTextChangedListener(dateTextWatcher)
        //editTextEndD.addTextChangedListener(dateTextWatcher)
    }

    private fun insertMedi() {

        val mediTitle = binding.editTextM.text.toString() // 할일 제목
        val mediStartD = binding.editTextStartD.text.toString()
        val mediEndD = binding.editTextEndD.text.toString()
        val medicheckBox = binding.checkBox.isChecked
        val mediEtc = binding.editTextMultiLine.text.toString()

        // Date validation
        if (!isValidDate(mediStartD) || (!mediEndD.isBlank() && !isValidDate(mediEndD))) {
            Toast.makeText(
                this,
                "유효하지 않은 날짜 형식입니다. 항목을 다시 확인해주세요.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // 현재 날짜를 가져옴
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(System.currentTimeMillis())

        // mediStartD와 currentDate 비교
        if (mediStartD > currentDate) {
            Toast.makeText(
                this,
                "복용 시작일에 미래 날짜는 입력할 수 없습니다.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val Medi = Medicine(null, mediTitle, mediStartD, mediEndD, medicheckBox, mediEtc)

        if (mediTitle.isBlank() || mediStartD.isBlank()) {
            Toast.makeText(this, "항목을 채워주세요", Toast.LENGTH_SHORT).show()
        } else {
            Thread {
                medicineDao.insertMedi(Medi)
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
        val intent = Intent(this, MedicineReadActivity::class.java)
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
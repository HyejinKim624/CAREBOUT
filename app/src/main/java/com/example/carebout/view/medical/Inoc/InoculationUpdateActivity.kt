package com.example.carebout.view.medical.Inoc

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
import android.widget.ToggleButton
import com.example.carebout.R
import com.example.carebout.databinding.ActivityInoculationUpdateBinding
import com.example.carebout.view.medical.db.AppDatabase
import com.example.carebout.view.medical.db.Inoculation
import com.example.carebout.view.medical.db.InoculationDao
import java.text.SimpleDateFormat
import java.util.Locale

class InoculationUpdateActivity : AppCompatActivity() {

    lateinit var binding: ActivityInoculationUpdateBinding
    lateinit var db: AppDatabase
    lateinit var inocDao: InoculationDao
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInoculationUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(applicationContext)!!
        inocDao = db.getInocDao()

        val tag1 : ToggleButton = findViewById(R.id.toggle1)
        val tag2 : ToggleButton = findViewById(R.id.toggle2)
        val editTextList: EditText = findViewById(R.id.editTextList)
        val editTextDate: EditText = findViewById(R.id.editTextDate)
        val editTextDue: EditText = findViewById(R.id.editTextDue)
        val editTextH: EditText = findViewById(R.id.editTextH)
        val editTextMultiLine: TextView = findViewById(R.id.editTextMultiLine)

        val updateBtn: Button = findViewById(R.id.updateBtn)
        val deleteBtn: Button = findViewById(R.id.deleteBtn)

        // 수정 페이지로 전달된 아이템 정보를 가져옴
        val inocId = intent.getIntExtra("inocId", -1)
        id = inocId
        Log.i("id", inocId.toString())
        if (inocId != -1) {
            // clinicId를 사용하여 데이터베이스에서 해당 아이템 정보를 가져와서 수정 페이지에서 사용할 수 있음
            // 수정 기능을 구현하는 코드 추가
            //넘어온 데이터 변수에 담기
            var uTag: String? = intent.getStringExtra("uTag")
            var uDate: String? = intent.getStringExtra("uDate")
            var uDue: String? = intent.getStringExtra("uDue")
            var uHospital: String? = intent.getStringExtra("uHospital")
            var uEtc: String? = intent.getStringExtra("uEtc")

            var uTag1: Boolean = intent.getBooleanExtra("uTag1", true)
            var uTag2: Boolean = intent.getBooleanExtra("uTag2", false)

            //화면에 값 적용
            editTextList.setText(uTag)
            editTextDate.setText(uDate)
            editTextDue.setText(uDue)
            editTextH.setText(uHospital)
            editTextMultiLine.setText(uEtc)

            tag1.isChecked = uTag1
            tag2.isChecked = uTag2

            Log.i("in", uTag.toString())
        }

        tag1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (tag2.isChecked) {
                    tag2.isChecked = false
                }
            }
        }

        tag2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (tag1.isChecked) {
                    tag1.isChecked = false
                }
            }
        }

        updateBtn.setOnClickListener{
            updateInoc()
        }

        deleteBtn.setOnClickListener {
            deletInoc()
        }

        // 숫자 입력 시 대시 "-" 자동 추가
        setupDateEditText(binding.editTextDate)
        setupDateEditText(binding.editTextDue)
    }


    private fun updateInoc() {
        val inocTag = binding.editTextList.text.toString()
        val inocDate = binding.editTextDate.text.toString()
        val inocDue = binding.editTextDue.text.toString()
        val inocH = binding.editTextH.text.toString()
        val inocEtc = binding.editTextMultiLine.text.toString()

        val tag1 = binding.toggle1.isChecked
        val tag2 = binding.toggle2.isChecked

        // Date validation
        if (!isValidDate(inocDate) || (!inocDue.isBlank() && !isValidDate(inocDue))) {
            Toast.makeText(
                this,
                "유효하지 않은 날짜 형식입니다. 항목을 다시 확인해주세요.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // 현재 날짜를 가져옴
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(System.currentTimeMillis())

        // inocDate와 currentDate 비교
        if (inocDate > currentDate) {
            Toast.makeText(
                this,
                "접종/구충 날짜에 미래 날짜는 입력할 수 없습니다.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val Inoc = Inoculation(id, tag1, tag2, inocTag, inocDate, inocDue, inocH, inocEtc)

        if (!tag1 && !tag2 || inocDate.isBlank()) {
            Toast.makeText(this, "항목을 채워주세요", Toast.LENGTH_SHORT).show()
        } else {
            Thread {
                inocDao.updateInoculation(Inoc)
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

    private fun deletInoc() {
        Thread {
            val inocToDelete = inocDao.getInoculationById(id)
            if (inocToDelete != null) {
                inocDao.deleteInoculation(inocToDelete)
                runOnUiThread {
                    Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    moveToAnotherPage()
                }
            }
        }.start()
    }

    private fun moveToAnotherPage() {
        val intent = Intent(this, InoculationReadActivity::class.java)
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
                        updatedText.insert(start, '-')

                        editText.setText(updatedText)
                        editText.setSelection(updatedText.length) // 커서를 항상 마지막으로 이동
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
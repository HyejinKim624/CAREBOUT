package com.example.carebout.view.medical.Todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carebout.R
import com.example.carebout.databinding.ActivityTodoUpdateBinding
import com.example.carebout.view.medical.db.AppDatabase
import com.example.carebout.view.medical.db.DailyTodo
import com.example.carebout.view.medical.db.TodoDao

class TodoUpdateActivity : AppCompatActivity() {

    lateinit var binding : ActivityTodoUpdateBinding
    lateinit var db : AppDatabase
    lateinit var todoDao: TodoDao
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodoUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(applicationContext)!!
        todoDao = db.getTodoDao()

        var counter:Int = 0 // 증감할 숫자의 변수 지정

        val todoText: TextView = findViewById(R.id.TodoEditText)
        val numText: TextView = findViewById(R.id.numText)
        val editTextMultiLine: TextView = findViewById(R.id.editTextMultiLine)

        val updateBtn: Button = findViewById(R.id.updateBtn)
        val deleteBtn: Button = findViewById(R.id.deleteBtn)

        val btnminus: Button = findViewById(R.id.button2)
        val btnplus: Button = findViewById(R.id.button3)

        // 수정 페이지로 전달된 아이템 정보를 가져옴
        val todoId = intent.getIntExtra("todoId", -1)
        id = todoId
        Log.i("id", todoId.toString())
        if (todoId != -1) {
            // todoId를 사용하여 데이터베이스에서 해당 아이템 정보를 가져와서 수정 페이지에서 사용할 수 있음
            // 수정 기능을 구현하는 코드 추가
            //넘어온 데이터 변수에 담기
            var uTitle: String? = intent.getStringExtra("uTitle")
            var uCount: String? = intent.getStringExtra("uCount")
            var uEtc: String? = intent.getStringExtra("uEtc")

            //화면에 값 적용
            todoText.setText(uTitle)
            numText.setText(uCount)
            counter = numText.text.toString().toInt()
            editTextMultiLine.setText(uEtc)

            Log.i("in", uTitle.toString())
        }

        updateBtn.setOnClickListener{
            updateTodo()
        }

        deleteBtn.setOnClickListener {
            deletTodo()
        }

        btnplus.setOnClickListener {
            counter++ //숫자는 1증가
            numText.text= counter.toString()
        }

        btnminus.setOnClickListener {
            if(counter > 0) {
                counter-- //숫자는 1감소
            }
            numText.text= counter.toString()
        }

    }

    private fun updateTodo() {

        val todoTitle = binding.TodoEditText.text.toString() // 할일 제목
        val todoCount = binding.numText.text.toString()
        //.toIntOrNull() ?: 0 // 숫자로 변환하거나 변환 실패 시 기본값 설정
        val todoEtc = binding.editTextMultiLine.text.toString()

        val Todo = DailyTodo(id, todoTitle, todoCount, todoEtc)

        //데이터 수정
        //db?.getTodoDao()?.updateTodo(Todo)

        if(todoTitle.isBlank() || todoCount.toInt() == 0) {
            Toast.makeText(
                this, "항목을 채워주세요",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Thread {
                todoDao.updateTodo(Todo)
                Log.i("id", Todo.toString())
                runOnUiThread { //아래 작업은 UI 스레드에서 실행해주어야 합니다.
                    Toast.makeText(
                        this, "수정되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    moveToAnotherPage()
                }
            }.start()
        }

    }

    private fun deletTodo() {

        //Log.i("size", todoList.size.toString())
        // 삭제 기능 구현
        // 해당 ID에 해당하는 할 일 데이터를 삭제하도록 todoDao를 사용합니다.
        Thread {
            val todoToDelete = todoDao.getTodoById(id)
            if (todoToDelete != null) {
                todoDao.deleteTodo(todoToDelete)
                runOnUiThread {
                    Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    moveToAnotherPage()
                }
            }
        }.start()
    }


    private fun moveToAnotherPage() {
        val intent = Intent(applicationContext, TodoReadActivity::class.java)
        startActivity(intent)
        finish()
    }
}
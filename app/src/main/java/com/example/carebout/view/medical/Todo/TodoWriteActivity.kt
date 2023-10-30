package com.example.carebout.view.medical.Todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carebout.R
import com.example.carebout.databinding.ActivityTodoWriteBinding
import com.example.carebout.view.medical.db.AppDatabase
import com.example.carebout.view.medical.db.DailyTodo
import com.example.carebout.view.medical.db.TodoDao

class TodoWriteActivity : AppCompatActivity() {

    lateinit var binding : ActivityTodoWriteBinding
    lateinit var db : AppDatabase
    lateinit var todoDao: TodoDao
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodoWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_todo_write)

        //db = AppDatabase.getInstance(this)!!
        db = AppDatabase.getInstance(applicationContext)!!
        todoDao = db.getTodoDao()
        //TodoList()

        var counter:Int = 0 // 증감할 숫자의 변수 지정

        val todoText: TextView = findViewById(R.id.TodoEditText)
        val numText: TextView = findViewById(R.id.numText)
        val editTextMultiLine: TextView = findViewById(R.id.editTextMultiLine)
        val btn1: Button = findViewById(R.id.button)
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

        // 페이지 이동
//        fun moveToAnotherPage(){
//            val intent = Intent(this, TodoReadActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        // 함수호출
        if (todoId != -1){
            btn1.setOnClickListener {
                Log.i("btn", todoId.toString())
                updateTodo()
            }
        } else {
            btn1.setOnClickListener {

//            val sTitle = todoText.text.toString()
//            val sCount = numText.text.toString().toInt()
//            val sEtc = editTextMultiLine.text.toString()

            insertTodo()
            //TodoList()
//            moveToAnotherPage()
            }
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

        counter = numText.text.toString().toInt()

    }

    private fun insertTodo() {

        val todoTitle = binding.TodoEditText.text.toString() // 할일 제목
        val todoCount = binding.numText.text.toString()
            //.toIntOrNull() ?: 0 // 숫자로 변환하거나 변환 실패 시 기본값 설정
        val todoEtc = binding.editTextMultiLine.text.toString()

        val Todo = DailyTodo(null, todoTitle, todoCount, todoEtc)

        if(todoTitle.isBlank() || todoCount.toInt() == 0) {
            Toast.makeText(
                this, "항목을 채워주세요",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Thread {
                todoDao.insertTodo(DailyTodo(null, todoTitle, todoCount, todoEtc))
                runOnUiThread { //아래 작업은 UI 스레드에서 실행해주어야 합니다.
                    Toast.makeText(
                        this, "추가되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    moveToAnotherPage()
//                    finish()
                }
            }.start()
        }

//        CoroutineScope(Dispatchers.IO).launch {
//            db.getTodoDao().insertTodo(DailyTodo(todoTitle, todoCount, todoEtc))
//        }
    }

    private fun moveToAnotherPage() {
        val intent = Intent(this, TodoReadActivity::class.java)
        startActivity(intent)
        finish()
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
//    private fun TodoList(){
//        var todoList = "데일리 케어\n"
//
//        CoroutineScope(Dispatchers.Main).launch {
//            val table_todos = CoroutineScope(Dispatchers.IO).async {
//                db.getTodoDao().getAll()
//            }.await()
//
//            for(table_todo in table_todos){
//                todoList += "할 일: ${table_todo.title}, 횟수: ${table_todo.count}, 비고: ${table_todo.etc}\n"
//            }
//            binding.textTodo.text = todoList
//        }
//    }

        // 싱글톤 패턴을 사용하지 않은 경우
//        val db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java,
//            "db_todo"
//        ).build()
//        db.getTodoDao().insertTodo(Todo)

        // 싱글톤 패턴을 사용한 경우
//        val db = AppDatabase.getInstance(applicationContext)
//        db!!.getTodoDao().insertTodo(Todo)

//        var db: AppDatabase? = AppDatabase.getInstance(applicationContext)
//
//        db?.getTodoDao()?.insertTodo(Todo)
//
//        finish()

//        if(title.isBlank() || count == 0) {
//            Toast.makeText(this, "항목을 채워주세요",
//                Toast.LENGTH_SHORT).show()
//        }
//        else {


//            Thread {
//                todoDao.insertTodo(Todo)
//                setResult(Activity.RESULT_OK)
//                //todoDao.insertTodo(DailyTodo(null, todoTitle, todoCount, todoEtc))
//                runOnUiThread { //아래 작업은 UI 스레드에서 실행해주어야 합니다.
//                    Toast.makeText(
//                        this, "추가되었습니다.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    finish()
//                }
//            }.start()
//        }
//    }

//    private fun insertTodo() {
//
//        val todoTitle = binding.TodoEditText.text.toString() // 할일 제목
//        val todoCount = binding.numText.text.count()
//        val todoEtc = binding.editTextMultiLine.text.toString()
//
//        if(todoTitle.isBlank() || todoCount == 0) {
//            Toast.makeText(this, "항목을 채워주세요",
//                Toast.LENGTH_SHORT).show()
//        }
//        else {
//            Thread {
//                todoDao.insertTodo(DailyTodo(null, todoTitle, todoCount, todoEtc))
//                runOnUiThread { //아래 작업은 UI 스레드에서 실행해주어야 합니다.
//                    Toast.makeText(
//                        this, "추가되었습니다.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    finish()
//                }
//            }.start()
//        }
//    }


}
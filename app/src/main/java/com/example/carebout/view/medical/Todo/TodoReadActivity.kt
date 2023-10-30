package com.example.carebout.view.medical.Todo

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carebout.R
import com.example.carebout.databinding.ActivityTodoReadBinding
import com.example.carebout.view.medical.db.AppDatabase
import com.example.carebout.view.medical.db.DailyTodo
import com.example.carebout.view.medical.db.TodoDao
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoReadActivity : AppCompatActivity() {
    //, OnItemLongClickListener
    private lateinit var binding: ActivityTodoReadBinding

    private lateinit var db: AppDatabase
    private lateinit var todoDao: TodoDao
    private var todoList: ArrayList<DailyTodo> = ArrayList<DailyTodo>()
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //db 인스턴스를 가져오고 db작업을 할 수 있는 dao를 가져옵니다.
        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        val insertBtn: FloatingActionButton = findViewById(R.id.insert_btn)
        insertBtn.setOnClickListener {
            val intent: Intent = Intent(this, TodoWriteActivity::class.java)

            activityResult.launch(intent)
        }

        //getAllTodoList() // 할 일 ㅅ리스트 가져오기

        //RecyclerView 설정
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //UserAdapter 초기화
        adapter = TodoAdapter(this)

        //Adapter 적용
        recyclerView.adapter = adapter

        // todoList 초기화
        //todoList = ArrayList()

        //조회
        getAllTodoList()

    }

    //액티비티가 백그라운드에 있는데 호출되면 실행 /수정화면에서 호출시 실행
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        //사용자 조회
        getAllTodoList()
    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode == RESULT_OK){
            //들어온 값이 OK라면
            //리스트 조회
            getAllTodoList()
        }
    }
    //리스트 조회
    private fun getAllTodoList() {

        val todoList: ArrayList<DailyTodo> = db?.getTodoDao()!!.getTodoAll() as ArrayList<DailyTodo>

        if(todoList.isNotEmpty()){
            //데이터 적용
            adapter.setTodoList(todoList)

            //val position: Int = todoList.size -1

            //Toast.makeText(this, "저장 정보 :" + todoList.get(position).title, Toast.LENGTH_SHORT).show()
        } else {
            //Todo 리스트가 비어있을 때 "할 일을 입력하세요" 메시지를 표시
//            val emptyMessage = TextView(this)
//            emptyMessage.text = "할 일을 입력하세요"
//            emptyMessage.textSize = 24f // 텍스트 크기 설정
//            emptyMessage.gravity = Gravity.CENTER
//            emptyMessage.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//
//            // 부모 레이아웃에 TextView 추가
//            val parentLayout = findViewById<ViewGroup>(R.id.parent_layout)
//            parentLayout.addView(emptyMessage)
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
//
//    private fun setRecyclerView() {
//        //리사이클러뷰 설정
//        runOnUiThread {
//            //adapter = TodoAdapter(todoList)
//            binding.recyclerView.adapter = adapter
//            binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        }
//    }
//
//    override fun onRestart() {
//        super.onRestart()
//        getAllTodoList()
//    }
//
//    override fun onLongClick(position: Int) {
//
////        val id = recyclerView
////            .ViewHolder.bindingAdapterPosition
//
//
//        Log.i("position", position.toString())
//        //Log.i("size", todoList.size.toString())
//        //Log.i("id", todoList[position].id.toString())
//        //Log.i("list", todoList[position].title.toString())
//
//        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
//        builder.setTitle("할 일 삭제")
//        builder.setMessage("정말 삭제하시겠습니까?")
//        builder.setNegativeButton("취소", null)
//        builder.setPositiveButton("네",
//            object : DialogInterface.OnClickListener {
//                override fun onClick(p0: DialogInterface?, p1: Int) {
//                    deletTodo(position)
//                }
//            }
//        )
//        builder.show()
//    }

//    private fun deletTodo(position: Int) {
//
////        val todoToDelete = DailyTodo(position, todoList[position].title, todoList[position].count, todoList[position].etc )
////        Log.i( "title", todoList[position].title.toString())
//        Log.i("size", todoList.size.toString())
//
//        var uId: Int? = todoList.get(position).id
//        var uTitle: String? = todoList.get(position).title
//        var uCount: String? = todoList.get(position).count
//        var uEtc: String? = todoList.get(position).etc
//
//        var todo: DailyTodo = DailyTodo(uId, uTitle, uCount, uEtc)
//
//        // Room 데이터베이스에서 삭제
//        Thread {
//
//            // 데이터 소스에서 아이템 삭제
//            //todoList.removeAt(position)
//
//
//            //todoList.removeAt(position)
//
//            Log.i("position", position.toString())
//
//            //todoList.removeAt(position)
//            //데이터베이스에서 아이템 삭제 후 UI 업데이트
//            runOnUiThread{
//                todoList.removeAt(position)
//                adapter.deleteTodoList(position)
//
//                adapter.notifyDataSetChanged()
//                Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
//
//            }
//            todoDao.deleteTodo(todo)
//
//        }.start()
//    }

//    fun setRecyclerView(todolist: ArrayList<DailyTodo>) {
//        //recyclerView.adpater = TodoAdapter(this, this)
//    }

}
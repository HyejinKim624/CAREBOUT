package com.example.carebout.view.medical.Todo

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carebout.R
import com.example.carebout.view.medical.db.DailyTodo

//private val itemLongClickListener: OnItemLongClickListener
class TodoAdapter(private val context: Context)
    : RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {

    //초기화
    private var todoList: ArrayList<DailyTodo> = ArrayList<DailyTodo>()


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleText: TextView = itemView.findViewById(R.id.title_text)
        val countText: TextView = itemView.findViewById(R.id.count_text)
        val etcText: TextView = itemView.findViewById(R.id.etc_text)

        //val root: = itemView
    }

    //화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_recyclerview, parent, false)
                //
            //.todo_list, parent, false)

        return MyViewHolder(view)
    }

    //데이터 설정
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val todoData = todoList[position]

        holder.titleText.text = todoData.title

        //데이터 변수에 담기
        var uTitle = todoList[holder.bindingAdapterPosition].title
        var uCount = todoList[holder.bindingAdapterPosition].count.toString()
        var uEtc = todoList[holder.bindingAdapterPosition].etc

        //데이터 적용
        holder.titleText.text = uTitle
        holder.countText.text = uCount
        holder.etcText.text = uEtc

        holder.titleText.text = todoData.title
        //holder

        holder.itemView.setOnClickListener {

            Log.i("size", todoList.size.toString())

            var intent: Intent = Intent(context, TodoUpdateActivity::class.java)

            intent.putExtra("todoId", todoList[holder.bindingAdapterPosition].todoId) // 아이템의 고유 ID 전달

            //값 담기
            intent.putExtra("uTitle", uTitle)
            intent.putExtra("uCount", uCount)
            intent.putExtra("uEtc", uEtc)

            context.startActivity(intent)
        }

//        // 아이템을 오래 클릭할 때 이벤트 처리
//        holder.itemView.setOnLongClickListener {
//
//            //val position =
//                //todoList[holder.bindingAdapterPosition].id
//            itemLongClickListener.onLongClick(position)
//            true // true를 반환하여 롱 클릭 이벤트를 소비하도록 함
//        }

    }

    //아이템 갯수
    override fun getItemCount(): Int {
        return todoList.size
    }

    //아이템 등록
    fun setTodoList(todoList: ArrayList<DailyTodo>){
        this.todoList = todoList
        //데이터 재설정
        notifyDataSetChanged()
    }

    fun deleteTodoList(position: Int) {
        this.todoList.removeAt(position)
        //this.todoList
    }



}
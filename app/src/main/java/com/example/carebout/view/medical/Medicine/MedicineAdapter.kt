package com.example.carebout.view.medical.Medicine

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carebout.R
import com.example.carebout.view.medical.db.Medicine

class MedicineAdapter(private val context: Context)
    : RecyclerView.Adapter<MedicineAdapter.MyViewHolder>() {

    //초기화
    private var mediList: ArrayList<Medicine> = ArrayList<Medicine>()


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleText: TextView = itemView.findViewById(R.id.title_text)
        val startText: TextView = itemView.findViewById(R.id.start_text)
        val endText: TextView = itemView.findViewById(R.id.end_text)
        //val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val etcText: TextView = itemView.findViewById(R.id.etc_text)

        //val root: = itemView
    }

    //화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.medi_recyclerview, parent, false)
        //
        //.todo_list, parent, false)

        return MyViewHolder(view)
    }

    //데이터 설정
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val mediData = mediList[position]

        holder.titleText.text = mediData.title

        //데이터 변수에 담기
        var uTitle = mediList[holder.bindingAdapterPosition].title
        var uStart = mediList[holder.bindingAdapterPosition].start
        var uEnd = mediList[holder.bindingAdapterPosition].end
        val uCurrent = mediData.checkBox
        var uEtc = mediList[holder.bindingAdapterPosition].etc

        // CheckBox 상태를 가져와서 boolean 변수에 할당
        //val checkBoxState: Boolean = holder.checkBox.isChecked


        //데이터 적용
        holder.titleText.text = uTitle
        holder.startText.text = uStart
        holder.endText.text = uEnd
        holder.etcText.text = uEtc
//        if (uCurrent != null) {
//            holder.checkBox.isChecked = uCurrent
//        }

        holder.titleText.text = mediData.title
        //holder

        if (uCurrent == true) {
            // uCurrent가 true일 경우 "복용중" 텍스트를 표시
            holder.endText.text = "복용중"
        } else {
            // uCurrent가 false이거나 null일 경우 uEnd 값을 사용
            holder.endText.text = mediData.end
        }


        holder.itemView.setOnClickListener {

            Log.i("size", mediList.size.toString())

            var intent: Intent = Intent(context, MedicineUpdateActivity::class.java)

            intent.putExtra("mediId", mediList[holder.bindingAdapterPosition].mediId) // 아이템의 고유 ID 전달
            Log.i("id", mediList[holder.bindingAdapterPosition].mediId.toString())
            //값 담기
            intent.putExtra("uTitle", uTitle)
            intent.putExtra("uStart", uStart)
            intent.putExtra("uEnd", uEnd)
            intent.putExtra("uCurrent", uCurrent)

//            if (uCurrent) {
//                // uCurrent 값이 true일 때 "복용중" 텍스트를 uEnd에 설정하여 Intent에 추가
//                intent.putExtra("uEnd", "복용중")
//            } else {
//                // uCurrent 값이 false일 때 uEnd 값을 Intent에 추가
//                intent.putExtra("uEnd", uEnd)
//            }

            intent.putExtra("uEtc", uEtc)

            context.startActivity(intent)
        }

    }

    //아이템 갯수
    override fun getItemCount(): Int {
        return mediList.size
    }

    //아이템 등록
    fun setMediList(mediList: ArrayList<Medicine>){
        this.mediList = mediList
        //데이터 재설정
        notifyDataSetChanged()
    }

    fun deleteMediList(position: Int) {
        this.mediList.removeAt(position)
        //this.todoList
    }



}
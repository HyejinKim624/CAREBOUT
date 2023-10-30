package com.example.carebout.view.medical.Inoc

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carebout.R
import com.example.carebout.view.medical.db.Inoculation

class InoculationAdapter(private val context: Context)
    : RecyclerView.Adapter<InoculationAdapter.MyViewHolder>() {

    //초기화
    private var inocList: ArrayList<Inoculation> = ArrayList<Inoculation>()


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//        val tag1: ToggleButton = itemView.findViewById(R.id.toggle1)
//        val tag2: ToggleButton = itemView.findViewById(R.id.toggle2)

        val tagText: TextView = itemView.findViewById(R.id.tag_text)
        val dateText: TextView = itemView.findViewById(R.id.date_text)
        val dueText: TextView = itemView.findViewById(R.id.due_text)
        val hospitalText: TextView = itemView.findViewById(R.id.hospital_text)
        val etcText: TextView = itemView.findViewById(R.id.etc_text)

    }

    //화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.inoc_recyclerview, parent, false)
        //
        //.todo_list, parent, false)

        return MyViewHolder(view)
    }

    //데이터 설정
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val inocData = inocList[position]

        holder.tagText.text = inocData.tag

        //데이터 변수에 담기
        var uTag = inocList[holder.bindingAdapterPosition].tag
        var uDate = inocList[holder.bindingAdapterPosition].date
        var uDue = inocList[holder.bindingAdapterPosition].due
        var uHospital = inocList[holder.bindingAdapterPosition].hospital
        var uEtc = inocList[holder.bindingAdapterPosition].etc

        var uTag1 = inocList[holder.bindingAdapterPosition].tag1
        var uTag2 = inocList[holder.bindingAdapterPosition].tag2



        //데이터 적용
        holder.tagText.text = uTag
        holder.dateText.text = uDate
        holder.dueText.text = uDue
        holder.hospitalText.text = uHospital
        holder.etcText.text = uEtc


        holder.tagText.text = inocData.tag

        if (uTag1 == true) {
            holder.tagText.text = "기초"
        } else {
        }

        if (uTag2 == true) {
            holder.tagText.text = "정기"
        } else {
        }

        //holder


        holder.itemView.setOnClickListener {

            Log.i("size", inocList.size.toString())

            var intent: Intent = Intent(context, InoculationUpdateActivity::class.java)

            intent.putExtra("inocId", inocList[holder.bindingAdapterPosition].inocId) // 아이템의 고유 ID 전달
            Log.i("id", inocList[holder.bindingAdapterPosition].inocId.toString())
            //값 담기
            intent.putExtra("uTag", uTag)
            intent.putExtra("uDate", uDate)
            intent.putExtra("uDue", uDue)
            intent.putExtra("uHospital", uHospital)
            intent.putExtra("uEtc", uEtc)

            intent.putExtra("uTag1", uTag1)
            intent.putExtra("uTag2", uTag2)


            context.startActivity(intent)
        }

    }

    //아이템 갯수
    override fun getItemCount(): Int {
        return inocList.size
    }

    //아이템 등록
    fun setInoculationList(inocList: ArrayList<Inoculation>){
        this.inocList = inocList
        //데이터 재설정
        notifyDataSetChanged()
    }

    fun deleteInoculationList(position: Int) {
        this.inocList.removeAt(position)
    }



}
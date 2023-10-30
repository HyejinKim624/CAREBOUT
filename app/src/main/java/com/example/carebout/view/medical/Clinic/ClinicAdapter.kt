package com.example.carebout.view.medical.Clinic

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carebout.R
import com.example.carebout.view.medical.db.Clinic

class ClinicAdapter(private val context: Context)
    : RecyclerView.Adapter<ClinicAdapter.MyViewHolder>() {

    //초기화
    private var clinicList: ArrayList<Clinic> = ArrayList<Clinic>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tagText: TextView = itemView.findViewById(R.id.tag_text)
        val dateText: TextView = itemView.findViewById(R.id.date_text)
        val hospitalText: TextView = itemView.findViewById(R.id.hospital_text)
        val etcText: TextView = itemView.findViewById(R.id.etc_text)

    }

    //화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.clinic_recyclerview, parent, false)

        return MyViewHolder(view)
    }

    //데이터 설정
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val clinicData = clinicList[position]

        holder.tagText.text = clinicData.tag

        //데이터 변수에 담기
        var uTag = clinicList[holder.bindingAdapterPosition].tag
        var uDate = clinicList[holder.bindingAdapterPosition].date
        var uHospital = clinicList[holder.bindingAdapterPosition].hospital
        var uEtc = clinicList[holder.bindingAdapterPosition].etc


        //데이터 적용
        holder.tagText.text = uTag
        holder.dateText.text = uDate
        holder.hospitalText.text = uHospital
        holder.etcText.text = uEtc


        holder.tagText.text = clinicData.tag
        //holder


        holder.itemView.setOnClickListener {

            Log.i("size", clinicList.size.toString())

            var intent: Intent = Intent(context, ClinicUpdateActivity::class.java)

            intent.putExtra("clinicId", clinicList[holder.bindingAdapterPosition].clinicId) // 아이템의 고유 ID 전달
            Log.i("id", clinicList[holder.bindingAdapterPosition].clinicId.toString())
            //값 담기
            intent.putExtra("uTag", uTag)
            intent.putExtra("uDate", uDate)
            intent.putExtra("uHospital", uHospital)
            intent.putExtra("uEtc", uEtc)


            context.startActivity(intent)
        }

    }

    //아이템 갯수
    override fun getItemCount(): Int {
        return clinicList.size
    }

    //아이템 등록
    fun setClinicList(clinicList: ArrayList<Clinic>){
        this.clinicList = clinicList
        //데이터 재설정
        notifyDataSetChanged()
    }

    fun deleteClinicList(position: Int) {
        this.clinicList.removeAt(position)
    }



}
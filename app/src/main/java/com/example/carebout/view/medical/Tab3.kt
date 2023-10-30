package com.example.carebout.view.medical

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.carebout.R

class Tab3 : Fragment() {
    private val st = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    fun setSheet(date: String, name: String, hpName: String) : View { // 약 정보 입력
        st.setMargins(0,30,0,30)
        var tablerow: TableRow = TableRow(this.context)     // 넣을 새 row 생성
        var sheetDate: TextView = TextView(this.context)     // 넣을 약 이름 text view
        var sheetName: TextView = TextView(this.context)   // 넣을 약 복용 기간 text view
        var sheetHpName: TextView = TextView(this.context)     // 넣을 약 비고 text view
        tablerow.layoutParams = st                          // 레이아웃 적용

        sheetDate.text = date
        sheetName.text = name
        sheetHpName.text = hpName
        sheetDate.textSize = 16f
        sheetName.textSize = 16f
        sheetHpName.textSize = 16f
        sheetHpName.setGravity(Gravity.CENTER)
        sheetDate.setGravity(Gravity.CENTER)
        sheetName.setGravity(Gravity.CENTER)

        tablerow.addView(sheetDate)
        tablerow.addView(sheetName)
        tablerow.addView(sheetHpName)

        return tablerow
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val tab3View: View = inflater.inflate(R.layout.tab3, container, false)
        val lay : TableLayout = tab3View.findViewById(R.id.sheetTableLay)

        lay.addView(setSheet("23/3/5", "건강검진", "나루동물병원"))
        lay.addView(setSheet("23/3/5", "건강검진", "나루동물병원"))
        lay.addView(setSheet("23/3/5", "건강검진", "나루동물병원"))
        lay.addView(setSheet("23/3/5", "건강검진", "나루동물병원"))
        lay.addView(setSheet("23/3/5", "건강검진", "나루동물병원"))

        return tab3View
    }
}
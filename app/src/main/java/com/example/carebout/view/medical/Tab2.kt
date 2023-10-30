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

class Tab2 : Fragment() {
    private val st = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    fun setInoculation(date: String, name: String, expectedDate: String) : View { // 약 정보 입력
        st.setMargins(0,30,0,30)
        var tablerow: TableRow = TableRow(this.context)     // 넣을 새 row 생성
        var innoDate: TextView = TextView(this.context)     // 넣을 약 이름 text view
        var innoName: TextView = TextView(this.context)   // 넣을 약 복용 기간 text view
        var innoEDate: TextView = TextView(this.context)     // 넣을 약 비고 text view
        tablerow.layoutParams = st                          // 레이아웃 적용

        innoDate.text = date
        innoName.text = name
        innoEDate.text = expectedDate
        innoDate.textSize = 16f
        innoName.textSize = 16f
        innoEDate.textSize = 16f
        innoEDate.setGravity(Gravity.CENTER)
        innoDate.setGravity(Gravity.CENTER)
        innoName.setGravity(Gravity.CENTER)

        tablerow.addView(innoDate)
        tablerow.addView(innoName)
        tablerow.addView(innoEDate)

        return tablerow
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val tab2View: View = inflater.inflate(R.layout.tab2, container, false)
        val lay : TableLayout = tab2View.findViewById(R.id.innoTableLay)

        lay.addView(setInoculation("23/3/5", "심장사상충", "23/4/5"))
        lay.addView(setInoculation("23/3/5", "심장사상충", "23/4/5"))
        lay.addView(setInoculation("23/3/5", "심장사상충", "23/4/5"))
        lay.addView(setInoculation("23/3/5", "심장사상충", "23/4/5"))
        lay.addView(setInoculation("23/3/5", "심장사상충", "23/4/5"))

        return tab2View
    }
}
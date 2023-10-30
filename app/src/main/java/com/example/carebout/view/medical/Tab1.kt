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
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import com.example.carebout.R

class Tab1 : Fragment() {
    private val st = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    fun setMedi(name: String, peri: String, memo: String, tag: String) : View { // 약 정보 입력
        st.setMargins(0,30,0,30)
        var tablerow: TableRow = TableRow(this.context)     // 넣을 새 row 생성
        var mediName: TextView = TextView(this.context)     // 넣을 약 이름 text view
        var mediPeriod: TextView = TextView(this.context)   // 넣을 약 복용 기간 text view
        var mediMemo: TextView = TextView(this.context)     // 넣을 약 비고 text view
        var mediTag: String = tag
        tablerow.layoutParams = st                          // 레이아웃 적용

        mediName.text = name
        mediPeriod.text = peri
        mediMemo.text = memo
        mediName.textSize = 16f
        mediPeriod.textSize = 16f
        mediMemo.textSize = 16f
        mediMemo.setGravity(Gravity.CENTER)
        mediName.setGravity(Gravity.CENTER)
        mediPeriod.setGravity(Gravity.CENTER)

        tablerow.addView(mediName)
        tablerow.addView(mediPeriod)
        tablerow.addView(mediMemo)

        return tablerow
    }

    fun setTagButtonOnClick(tagButton: ToggleButton) {
        tagButton.setOnClickListener {
            if (tagButton.isChecked) {
//                tagButton.setBackgroundDrawable(resources.getDrawable(R.drawable.fillborn))
            } else {
//                tagButton.setBackgroundDrawable(resources.getDrawable(R.drawable.bornn))
                // sql 문으로
            }
        }
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val tab1View: View = inflater.inflate(R.layout.tab1, container, false)
        val lay : TableLayout = tab1View.findViewById(R.id.mediTableLay)

        lay.addView(setMedi("감기약", "23/1/2~23/5/2", "간식에 섞어서 줄 것", "cold"))
        lay.addView(setMedi("감기약", "23/1/2~23/5/2", "간식에 섞어서 줄 것", "cold"))
        lay.addView(setMedi("감기약", "23/1/2~23/5/2", "간식에 섞어서 줄 것", "cold"))
        lay.addView(setMedi("감기약", "23/1/2~23/5/2", "간식에 섞어서 줄 것", "cold"))
        lay.addView(setMedi("감기약", "23/1/2~23/5/2", "간식에 섞어서 줄 것", "cold"))

        return tab1View
    }
}
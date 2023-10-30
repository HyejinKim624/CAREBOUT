package com.example.carebout.view.medical

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.carebout.R
import java.util.Date

val st = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT)


public class Medi(nm: String = "", pr: Int = 0, ing: Boolean = false) {
    var name: String = ""
    var period: Int = 0
    var isIng : Boolean = false

    init {
        this.name = nm
        this.period = pr
        this.isIng = ing
    }

    @JvmName("getString")
    public fun getName() : String {
        return name
    }

    @JvmName("getInt")
    public fun getPeriod() : Int {
        return period
    }
}

class Mediing : Fragment() {
    // 약 정보 입력
    var medi0 = Medi("감기약", 20230910, true)
    var medi1 = Medi("심장약", 20200310, false)
    var medi2 = Medi("연고", 20210910, false)
    var medi3 = Medi("비타민", 20230901, true)

    fun setMedicine(md: Medi) : View {
        var mediView = TextView(this.context) // 빈 텍스트뷰 생성
        mediView.text = "\uD83D\uDC8A ${md.getName()}   ${md.getPeriod()}~" // 텍스트 넣기
        mediView.textSize = 16.0f
        mediView.layoutParams = st // 레이아웃 지정
        mediView.id = ViewCompat.generateViewId() // 아이디 랜덤으로 지정

        return mediView
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mediView: View = inflater.inflate(R.layout.mediing, container, false)
        val lay : LinearLayout = mediView.findViewById(R.id.mediLay)

        lay.addView(setMedicine(medi0))

        lay.addView(setMedicine(medi3))
// 💊
        return mediView
    }
}
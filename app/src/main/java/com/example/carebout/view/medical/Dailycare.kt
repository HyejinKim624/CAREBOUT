package com.example.carebout.view.medical

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.carebout.R
import java.text.SimpleDateFormat
import java.util.Date



class Dailycare : Fragment() {
    var nthDaily: Int = 0; // 데일리케어 개수
    var dailycareText = Array<String>(10, {"-"}) // 데일리케어 타이틀
    var dailycareNumber = Array<Int>(10, {0}) // 타이틀을 몇 번 해야하는지
    val st = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT)
    val stBtn = LinearLayout.LayoutParams(130, 130)
    val views: Array<ToggleButton?> = Array(10,  {null})
    var bornCount = 0

    // 시간 가져오는 함수
    fun getTime() : String{
        var now: Long = System.currentTimeMillis();
        var date = Date(now);
        val sdf = SimpleDateFormat("mm");
//        val sdf = SimpleDateFormat("yyyyMMdd");
//        시험삼아 해보기 위해 날짜 포맷을 분을 가져옴, 실제 돌릴 땐 위 sdf 말고 주석처리한 아래의 sdf로 사용
        var getTime: String = sdf.format(date)

        return getTime
    }

    // unSelectBorn 위한 시간 관련 변수
    var saveTime: String = getTime()
    var crtTime: String = ""

    // 뼈다귀 토글버튼 리스너
    fun setButtonOnClick(bornButton: ToggleButton) {
        bornButton.setOnCheckedChangeListener { _, _ ->
            if (bornButton.isChecked) {
                bornButton.setBackgroundDrawable(resources.getDrawable(R.drawable.fillborn))
            } else {
                bornButton.setBackgroundDrawable(resources.getDrawable(R.drawable.bornn))
            }
        }
    }

    // 데일리케어 타이틀 아래에 들어갈 뼈다귀 모양 토글버튼 생성, 토글 버튼이 든 뷰 생성 및 리턴
    fun setBornIcon() : View {
        var btnView = LinearLayout(this.context)

        for (i in 1..dailycareNumber[nthDaily]) {
            var bornButton = ToggleButton(this.context) // 버튼 생성
            bornButton.layoutParams = stBtn // 레이아웃 지정
            btnView.layoutParams = st
            views[(bornCount++)] = bornButton // 나중에 unCheck 하기 위해 views 배열에 버튼들을 넣어놓는다(나중에 크기 등등 변경 필요)
            bornButton.text = ""
            bornButton.textOn = ""
            bornButton.textOff = ""
            bornButton.setBackgroundDrawable(resources.getDrawable(R.drawable.bornn))
            setButtonOnClick(bornButton) // 리스너 연결

            btnView.addView(bornButton)
        }
        return btnView // 뷰 리턴
    }

    // 시간을 비교하여 토글버튼 해제-의료수첩 탭을 눌렀을 때 마지막으로 눌렀을 때의 날짜와 비교해 지금 날짜가 더 크다면 버튼을 해제
    fun unSelectBorn() {
        crtTime = getTime() // 눌렀을 때의 시간 가져온다
        if(saveTime.toInt() < crtTime.toInt()) {
            for(i in 1..bornCount) {
                if((views[i-1]?.isChecked)?:false)
                    views[i-1]?.toggle()
            }
        }

        saveTime = crtTime // 마지막으로 누른 시간 = 지금 누른 시간
    }


    // 데일리케어 타이틀의 텍스트뷰 생성
    fun setDailycare() : View {
        var dailycareTextView = TextView(this.context) // 빈 텍스트뷰 생성
        dailycareTextView.text = "\n-" + dailycareText[nthDaily] // 텍스트 넣기
        dailycareTextView.textSize = 18.0f
        dailycareTextView.layoutParams = st // 레이아웃 지정
        dailycareTextView.id = ViewCompat.generateViewId() // 아이디 랜덤으로 지정

        return dailycareTextView
    }

    // 데일리케어 정보를 insert
    fun insertDailycare(title: String, number: Int) {
        dailycareText[nthDaily] = title
        dailycareNumber[nthDaily] = number
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        val dailyView: View = inflater.inflate(R.layout.dailycare, container, false)
        val lay : LinearLayout = dailyView.findViewById(R.id.dailyLayout)

        insertDailycare("약 먹이기", 2)
        lay.addView(setDailycare())
        lay.addView(setBornIcon())
        nthDaily++;

        insertDailycare("산책", 3)
        lay.addView(setDailycare())
        lay.addView(setBornIcon())
        nthDaily++;

        return dailyView
    }



}

package com.example.carebout.view.home

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carebout.R
import com.example.carebout.base.bottomTabClick
import com.example.carebout.databinding.ActivityHomeBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.helloName.text = "반가워, 쿵이야"
        setWeightGraph()

        val dataSet: MutableList<Pair<String, String>> =
            mutableListOf(Pair("X-ray", "01-04"), Pair("피검사", "01-05"), Pair("초음파", "01-06"),
                Pair("피검사", "02-10"), Pair("X-ray", "03-10"), Pair("CT", "03-12"), Pair("MRI", "03-14"),
                Pair("피검사", "04-05"), Pair("초음파", "04-10"), Pair("X-ray", "04-12"), Pair("피검사", "05-12"),
                Pair("초음파", "05-14"))

        val recyclerAdapter = RecyclerAdapter(dataSet)
        binding.checkGraph.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.checkGraph.adapter = recyclerAdapter

        bottomTabClick(binding.bottomTapBarOuter, this)
    }

    private fun setWeightGraph() {

        val xAxis: XAxis = binding.weightGraph.xAxis   //x축 가져오기

        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM   //x축은 아래에 위치
            setEnabled(false)   // x축 안 보이기
            textSize = 10f  //텍스트 크기
            setDrawGridLines(false)  //x축 그리드 no. true가 기본값
            isGranularityEnabled = true //x축 간격 세분화 ok
            granularity = 1f    //x축 데이터 간격
            spaceMin = 0.3f //x축 1번 데이터와 왼쪽 y축 사이 간격
            spaceMax = 0.3f //x축 마지막 데이터와 오른쪽 y축 사이 간격
        }

        binding.weightGraph.apply {
            axisRight.isEnabled = false //y축 오른쪽 비활성화
            axisLeft.isEnabled = false  //y축 왼쪽 비활성화
            axisLeft.axisLineColor = resources.getColor(R.color.white)  // 다크모드를 위해 배경색이랑 같게 설정
            axisLeft.axisMinimum = 2f   //y축 왼쪽 표시 데이터 최솟값
            axisLeft.axisMaximum = 7.2f //y축 왼쪽 표시 데이터 최댓값
            axisLeft.setLabelCount(4, true)
            axisLeft.setDrawLabels(false)
            axisLeft.setDrawGridLines(true)
            axisLeft.setDrawZeroLine(false)
            legend.apply {
                textSize = 15f
                verticalAlignment = Legend.LegendVerticalAlignment.TOP  //수직 조정 : 위로
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER // 수평 조정 : 가운데로
                orientation = Legend.LegendOrientation.HORIZONTAL   //범례와 차트 정렬 : 수평
                setEnabled(false)
                setDrawInside(false)    //차트 안에 X
            }
        }

        val lineData = LineData()
        binding.weightGraph.data = lineData

        addEntry(3f)
        addEntry(3.5f)
        addEntry(4f)
        addEntry(4.4f)
        addEntry(5f)
        addEntry(5.2f)
        addEntry(5.4f)
        addEntry(5.6f)
        addEntry(6f)
        addEntry(6.2f)
        addEntry(5.9f)
        addEntry(5.7f)
        addEntry(5.3f)
        addEntry(5.1f)


    }

    private fun addEntry(a:Float) {
        val data = binding.weightGraph.data
        // 라인 차트
        data?.let { // 데이터가 null이 아닐 때 실행
            var set: ILineDataSet? = data.getDataSetByIndex(0)
            // 임의의 데이터셋 (0번 부터 시작)
            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }

            data.addEntry(Entry(set!!.entryCount.toFloat(), a), 0) // 데이터 엔트리 추가 Entry(x값, y값)
            data.notifyDataChanged() //
            binding.weightGraph.apply {
                notifyDataSetChanged() //
                moveViewToX(data.entryCount.toFloat()) // 좌우 스크롤
                setVisibleXRangeMaximum(8f) // 한 화면(?)에 최대 7개의 점을 보여줄 수 있음
                setPinchZoom(false) // 두손가락으로 확대 X
                isDoubleTapToZoomEnabled = false // 더블탭 확대 X
                description = null
            }
        }
    }

    private fun createSet() = LineDataSet(null, null).apply {
        axisDependency = YAxis.AxisDependency.LEFT // Y
        color = Color.parseColor("#A7A7A7") // line color
        setCircleColor(resources.getColor(R.color.white)) // circle color
        circleHoleColor = Color.parseColor("#6EC677") // fill circle 6EC677
        valueTextSize = 10f // 값(숫자) 크기
        lineWidth = 2.5f // 선 두께
        circleRadius = 6f // 외경 크기
        circleHoleRadius = 4f   // 내경 크기
        fillAlpha = 0 // 라인 색투명도
        valueTextColor = Color.parseColor("#A7A7A7")    //값(숫자) 색
        highLightColor = Color.BLACK //
        setDrawValues(true) // 원 위에 값 써줄까? ok
    }


}
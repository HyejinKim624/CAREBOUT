package com.example.carebout.view.calendar.decorator

import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.carebout.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*
import java.util.Calendar

class CurrentDayDecorator(private val context: Activity) : DayViewDecorator {

    // 사용자가 선택한 날짜를 얻어온다. 예를 들어, 선택한 날짜를 calendar 변수에 저장한다고 가정
    private val calendar: Calendar = Calendar.getInstance() // 선택한 날짜를 얻어오는 코드로 대체되어야 함

    // Calendar 객체에서 선택한 날짜를 Date로 변환
    private val selectedDate: Date? = calendar.time

    private val drawable: Drawable?

    private val currentDay: CalendarDay = CalendarDay.from(2023,10,4)

    init {
        drawable = ContextCompat.getDrawable(context, R.drawable.first_day_month)
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == currentDay
    }

    override fun decorate(view: DayViewFacade) {
        drawable?.let { view.setSelectionDrawable(it) }
    }
}





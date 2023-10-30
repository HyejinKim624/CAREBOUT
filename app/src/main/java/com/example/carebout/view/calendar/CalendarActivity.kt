package com.example.carebout.view.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.carebout.R
import com.example.carebout.base.bottomTabClick
import com.example.carebout.databinding.ActivityCalendarBinding
import com.example.carebout.view.calendar.decorator.SaturdayDecorator
import com.example.carebout.view.calendar.decorator.SundayDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

class CalendarActivity : AppCompatActivity() {
    var userID: String = "userID"

    lateinit var binding: ActivityCalendarBinding

    lateinit var calendarView: MaterialCalendarView
    lateinit var saveBtn: Button
    lateinit var editBtn: Button
    lateinit var delBtn: Button
    lateinit var contextEditText: EditText
    lateinit var listView: ListView
    val data = mutableMapOf<String, MutableList<String>>() // 데이터 목록
    val adapter: ArrayAdapter<String> by lazy { ArrayAdapter(this, android.R.layout.simple_list_item_1) }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listView = findViewById(R.id.list)
        listView.adapter = adapter

        calendarView = findViewById(R.id.calendarView)
        saveBtn = findViewById(R.id.save_Btn)
        editBtn = findViewById(R.id.cha_Btn)
        delBtn = findViewById(R.id.can_Btn)
        contextEditText = findViewById(R.id.contextEditText)

        calendarView.addDecorator(SundayDecorator()) // 일요일은 빨간색
        calendarView.addDecorator(SaturdayDecorator()) // 토요일은 파란색

        registerForContextMenu(listView)
        listView.setOnItemClickListener { parent, view, position, id ->
            val popupMenu = PopupMenu(this, view, Gravity.RIGHT)
            popupMenu.menuInflater.inflate(R.menu.context_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.edit_item -> {
                        val selectedPosition = position
                        val adapter = listView.adapter as ArrayAdapter<String>
                        val selectedItem = adapter.getItem(selectedPosition)

                        if (selectedItem != null) {
                            val editDialog = AlertDialog.Builder(this)
                            val editView = EditText(this)
                            editView.setText(selectedItem)
                            editDialog.setView(editView)
                            editDialog.setPositiveButton("저장") { dialog, which ->
                                // 수정한 내용을 가져와서 데이터를 업데이트
                                val editedText = editView.text.toString()
                                adapter.remove(selectedItem)
                                adapter.insert(editedText, selectedPosition)
                                adapter.notifyDataSetChanged()

                                editBtn.visibility = View.VISIBLE
                                delBtn.visibility = View.VISIBLE
                                // 여기에서 수정된 내용을 저장하거나 데이터베이스를 업데이트하는 등의 추가 작업을 수행할 수 있습니다.
                            }
                            editDialog.setNegativeButton("취소", null)
                            editDialog.show()
                        }
                        return@setOnMenuItemClickListener true
                    }
                    R.id.delete_item -> {
                        val selectedPosition = position

                        // 어댑터에서 선택한 위치의 항목을 가져옵니다.
                        val adapter = listView.adapter as ArrayAdapter<String> // 데이터 유형을 명시적으로 지정
                        val selectedItem = adapter.getItem(selectedPosition)

                        if (selectedItem != null) {
                            adapter.remove(selectedItem)

                            adapter.notifyDataSetChanged()
                            return@setOnMenuItemClickListener true
                        } else {
                            return@setOnMenuItemClickListener false
                        }
                    }
                    else -> return@setOnMenuItemClickListener false
                }
            }

            popupMenu.show()
        }

        calendarView.setOnDateChangedListener { widget, date, selected ->
            updateListView(date)
            saveBtn.visibility = View.VISIBLE
            contextEditText.visibility = View.VISIBLE
            contextEditText.setText("")
        }

        saveBtn.setOnClickListener {
            val text = contextEditText.text.toString()
            val currentDate = calendarView.selectedDate
            saveDiary(currentDate, text)
            updateListView(currentDate)
            contextEditText.visibility = View.VISIBLE
            saveBtn.visibility = View.VISIBLE
            listView.visibility = View.VISIBLE
            contextEditText.setText("")
        }
        // bottomTap 클릭시 intent를 위한 함수
        bottomTabClick(binding.bottomTapBarOuter, this)
    }

    private fun updateListView(date: CalendarDay) {
        adapter.clear()
        val strList = loadDiary(date)
        adapter.addAll(strList)
        adapter.notifyDataSetChanged()
    }

    private fun saveDiary(date: CalendarDay, text: String) {
        val fname = "${userID}_${date.year}_${date.month}_${date.day}.txt"
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = openFileOutput(fname, MODE_APPEND)
            val entry = text + "\n"
            fileOutputStream.write(entry.toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadDiary(date: CalendarDay): MutableList<String> {
        val fname = "${userID}_${date.year}_${date.month}_${date.day}.txt"
        val fileInputStream: FileInputStream
        val strList = mutableListOf<String>()

        try {
            fileInputStream = openFileInput(fname)
            val scanner = Scanner(fileInputStream)
            while (scanner.hasNextLine()) {
                val line = scanner.nextLine()
                strList.add(line)
            }
            fileInputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return strList
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }
}

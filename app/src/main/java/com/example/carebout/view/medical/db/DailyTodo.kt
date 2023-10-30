package com.example.carebout.view.medical.db

import android.icu.text.CaseMap.Title
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_todo") //어노테이션

data class DailyTodo (
    @PrimaryKey(autoGenerate = true)
    var todoId: Int?,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "count")
    var count: String?,

    @ColumnInfo(name = "etc")
    var etc: String?
)
{

}

//    @ColumnInfo(name = "title") val title: String,
//    @ColumnInfo(name = "count") val count: Int,
//    @ColumnInfo(name = "etc") val etc: String
//)
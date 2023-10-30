package com.example.carebout.view.medical.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_medicine") //어노테이션
data class Medicine (
    @PrimaryKey(autoGenerate = true)
    var mediId: Int?,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "start")
    var start: String?,

    @ColumnInfo(name = "end")
    var end: String?,

    @ColumnInfo(name = "checkBox")
    var checkBox: Boolean?,

    @ColumnInfo(name = "etc")
    var etc: String?
)
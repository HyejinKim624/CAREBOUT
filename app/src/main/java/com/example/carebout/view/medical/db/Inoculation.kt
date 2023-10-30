package com.example.carebout.view.medical.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_inoculation")
data class Inoculation (
    @PrimaryKey(autoGenerate = true)
    var inocId: Int?,

    @ColumnInfo(name = "tag1")
    var tag1: Boolean?,

    @ColumnInfo(name = "tag2")
    var tag2: Boolean?,

    @ColumnInfo(name = "tag")
    var tag: String?,

    @ColumnInfo(name = "date")
    var date: String?,

    @ColumnInfo(name = "due")
    var due : String?,

    @ColumnInfo(name = "hospital")
    var hospital: String?,

    @ColumnInfo(name = "etc")
    var etc: String?
)
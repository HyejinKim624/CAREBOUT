package com.example.carebout.view.medical.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_clinic")
data class Clinic (
    @PrimaryKey(autoGenerate = true)
    var clinicId: Int?,

    @ColumnInfo(name = "tag")
    var tag: String?,

    @ColumnInfo(name = "date")
    var date: String?,

    @ColumnInfo(name = "hospital")
    var hospital: String?,

    @ColumnInfo(name = "etc")
    var etc: String?
)
package com.example.carebout.view.medical.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao

interface MedicineDao {
    @Query("SELECT * FROM table_medicine")
    fun getMediAll() : List<Medicine>

//    @Query("SELECT id, title, start, `end`, etc FROM table_medicine")
//    fun getMediList() : List<MedicineInfo>

    @Insert
    fun insertMedi(medi: Medicine)

    @Update
    fun updateMedi(medi: Medicine)

    @Delete
    fun deleteMedi(medi: Medicine)

    @Query("SELECT * FROM table_medicine WHERE mediId = :id")
    fun getMediById(id: Int): Medicine?

    @Query("SELECT * FROM table_medicine WHERE checkBox = 1")
    fun getMediWithCheck(): List<Medicine>

    //@Query("DELETE FROM User WHERE name = :name") // 'name'에 해당하는 유저를 삭제해라
    //    fun deleteUserByName(name: String)
}
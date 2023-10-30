package com.example.carebout.view.medical.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface InoculationDao {
    @Query("SELECT * FROM table_inoculation")
    fun getInoculationAll() : List<Inoculation>

    @Insert
    fun insertInoculation(inoc: Inoculation)

    @Update
    fun updateInoculation(inoc: Inoculation)

    @Delete
    fun deleteInoculation(inoc: Inoculation)

    @Query("SELECT * FROM table_inoculation WHERE inocId = :id")
    fun getInoculationById(id: Int): Inoculation?

    @Query("SELECT * FROM table_inoculation ORDER BY date DESC")
    fun getInocDateAsc(): List<Inoculation>
    //ASC - 오름

    @Query("SELECT * FROM table_inoculation WHERE tag1 = 1")
    fun getInocWithTag1(): List<Inoculation>

    @Query("SELECT * FROM table_inoculation WHERE tag2 = 1")
    fun getInocWithTag2(): List<Inoculation>

    //@Query("DELETE FROM User WHERE name = :name") // 'name'에 해당하는 유저를 삭제해라
    //    fun deleteUserByName(name: String)
}
package com.example.carebout.view.medical.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ClinicDao {
    @Query("SELECT * FROM table_clinic")
    fun getClinicAll() : List<Clinic>

    @Insert
    fun insertClinic(clinic: Clinic)

    @Update
    fun updateClinic(clinic: Clinic)

    @Delete
    fun deleteClinic(clinic: Clinic)

    @Query("SELECT * FROM table_clinic WHERE clinicId = :id")
    fun getClinicById(id: Int): Clinic?

    //@Query("DELETE FROM User WHERE name = :name") // 'name'에 해당하는 유저를 삭제해라
    //    fun deleteUserByName(name: String)
}
package com.example.carebout.view.medical.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Query("SELECT * FROM table_todo")
    fun getTodoAll() : List<DailyTodo>

    @Insert
    fun insertTodo(todo: DailyTodo)

    @Update
    fun updateTodo(todo: DailyTodo)

    @Delete
    fun deleteTodo(todo: DailyTodo)

    @Query("SELECT * FROM table_todo WHERE todoId = :id")
    fun getTodoById(id: Int): DailyTodo?

    //@Query("DELETE FROM User WHERE name = :name") // 'name'에 해당하는 유저를 삭제해라
    //    fun deleteUserByName(name: String)
}
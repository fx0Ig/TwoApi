package com.example.twoapi.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.twoapi.domain.User

@Dao
interface UserDao {

    @Query("DELETE FROM user_table")
    suspend fun deleteUsers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<User>)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun getUsers(): LiveData<List<User>>

}
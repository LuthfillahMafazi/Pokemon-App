package com.code.id.pokemonapp.data.local.user

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.code.id.pokemonapp.domain.model.UserEntity

class UserDbSqLite(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(UserTable.CREATE_TABLE)
    }

    override fun onUpgrade(
        p0: SQLiteDatabase?,
        p1: Int,
        p2: Int
    ) {
        p0?.execSQL("DROP TABLE IF EXISTS ${UserTable.TABLE_NAME}")
        onCreate(p0)
    }

    fun insertUser(user: UserEntity): Boolean {
        return writableDatabase.use { db ->
            val values = ContentValues().apply {
                put(UserTable.COLUMN_NAME, user.userName)
                put(UserTable.COLUMN_PASSWORD, user.password)
            }
            db.insert(UserTable.TABLE_NAME, null, values) != -1L
        }
    }

    fun getUserByUsernameAndPassword(username: String, password: String): UserEntity? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT ${UserTable.COLUMN_ID}, ${UserTable.COLUMN_NAME}, ${UserTable.COLUMN_PASSWORD} FROM ${UserTable.TABLE_NAME} WHERE ${UserTable.COLUMN_NAME} = ? AND ${UserTable.COLUMN_PASSWORD} = ?",
            arrayOf(username, password)
        )
        var user: UserEntity? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(UserTable.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.COLUMN_NAME))
            val pass = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.COLUMN_PASSWORD))
            user = UserEntity(id, name, pass)
        }
        cursor.close()
        return user
    }

    companion object {
        const val DATABASE_NAME = "user.db"
        const val DATABASE_VERSION = 1
    }
}
package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Todo(
    val id: Long,
    val title: String,
    val name: String,
    val telephone: String
)

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // статические константы имеет смысл хранить так:
    companion object {
        // версия БД
        const val DATABASE_VERSION = 1
        // название БД
        const val DATABASE_NAME = "to"
        // название таблицы
        const val TABLE_NAME = "tod"
        // названия полей
        const val KEY_ID = "id"
        const val KEY_TITLE = "Title"
        const val KEY_NAME = "Name"
        const val KEY_TELEPHONE = "Telephone"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
        CREATE TABLE $TABLE_NAME (
            $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $KEY_TITLE TEXT NOT NULL,
            $KEY_NAME TEXT NOT NULL,
            $KEY_TELEPHONE TEXT NOT NULL
        )""")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addTodo(title: String,name: String,telephone: String): Long {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, title)
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_TELEPHONE,telephone)
        val id = database.insert(TABLE_NAME, null, contentValues)
        close()
        return id
    }

    fun getAll(): List<Todo> {
        val result = mutableListOf<Todo>()
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, null, null,
            null, null, "title"
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val titleIndex: Int = cursor.getColumnIndex(KEY_TITLE)
            val nameIndex: Int = cursor.getColumnIndex(KEY_NAME)
            val telephoneIndex: Int = cursor.getColumnIndex(KEY_TELEPHONE)
            do {
                val todo = Todo(
                    cursor.getLong(idIndex),
                    cursor.getString(titleIndex),
                    cursor.getString(nameIndex),
                    cursor.getString(telephoneIndex)
                )
                result.add(todo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }


    fun remove(id: Long) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }

    fun getById(id: Long): Todo? {
        var result: Todo? = null
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, "$KEY_ID = ?", arrayOf(id.toString()),
            null, null, "title"
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val titleIndex: Int = cursor.getColumnIndex(KEY_TITLE)
            val nameIndex: Int = cursor.getColumnIndex(KEY_NAME)
            val telephoneIndex: Int = cursor.getColumnIndex(KEY_TELEPHONE)
            result = Todo(
                cursor.getLong(idIndex),
                cursor.getString(titleIndex),
                cursor.getString(nameIndex),
                cursor.getString(telephoneIndex)
            )
        }
        cursor.close()
        return result
    }

    fun update(id: Long, title: String,name: String,telephone: String) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, title)
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_TELEPHONE, telephone)
        database.update(TABLE_NAME, contentValues, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }


}


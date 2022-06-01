package com.example.data.datasources

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor

import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper
import com.example.data.entities.HeaderDataEntity
import com.example.data.entities.ResponseRequestDataEntity
import com.example.domain.entities.ResponseEntity


class DatabaseHelper constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table " + CONTACTS_TABLE_NAME + "(id integer primary key, url text,requestType integer,responseCode integer)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME)
        onCreate(db)
    }

    fun cache(url: String, requestType: Int, responseCode: Int): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("url", url)
        contentValues.put("requestType", requestType)
        contentValues.put("responseCode", responseCode)
        db.insert(CONTACTS_TABLE_NAME, null, contentValues)
        return true
    }

    @SuppressLint("Range")
    fun getCachedData(): ArrayList<ResponseRequestDataEntity> {
        var listResponseEntity = ArrayList<ResponseRequestDataEntity>()
        val TABLE_NAME = CONTACTS_TABLE_NAME
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        val data: Array<String?>? = null

        if (cursor.moveToFirst()) {
            do {
                val url = cursor.getString(cursor.getColumnIndex("url"))
                val requestType = cursor.getInt(cursor.getColumnIndex("requestType"))
                val responseCode = cursor.getInt(cursor.getColumnIndex("responseCode"))
                listResponseEntity.add(
                    ResponseRequestDataEntity(
                        requestType, responseCode, "", url, "", "",
                        "",
                        emptyList()
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return listResponseEntity
    }

    companion object {
        const val DATABASE_NAME = "MyDB"
        const val CONTACTS_TABLE_NAME = "Requests"
    }
}
package com.example.dojomovie.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Helper(
    context: Context?
) : SQLiteOpenHelper(context, "dojomovie", null, 1){
    var SQL_CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS user(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "phone_number TEXT," +
            "password TEXT)"

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(SQL_CREATE_TABLE_USER)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}
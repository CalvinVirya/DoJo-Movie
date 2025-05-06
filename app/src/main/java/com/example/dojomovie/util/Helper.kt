package com.example.dojomovie.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Helper(
    context: Context?
) : SQLiteOpenHelper(context, "dojomovie", null, 3){
    var SQL_CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS user(" +
            "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "phone_number VARCHAR," +
            "password VARCHAR)"

    var SQL_CREATE_TABLE_TRANSACTION = "CREATE TABLE IF NOT EXISTS transactions(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user_id VARCHAR, " +
            "film_id VARCHAR, " +
            "quantity INTEGER, " +
            "FOREIGN KEY(user_id) REFERENCES user(id), " +
            "FOREIGN KEY(film_id) REFERENCES film(id))"

    var SQL_CREATE_TABLE_FILM = "CREATE TABLE IF NOT EXISTS film(" +
            "film_id VARCHAR, " +
            "film_title VARCHAR, " +
            "film_image VARCHAR, " +
            "film_price INTEGER)"

    var SQL_DROP_TABLE_USER = "DROP TABLE IF EXISTS user"
    var SQL_DROP_TABLE_FILM = "DROP TABLE IF EXISTS user"
    var SQL_DROP_TABLE_TRANSACTION = "DROP TABLE IF EXISTS user"

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(SQL_CREATE_TABLE_USER)
        db?.execSQL(SQL_CREATE_TABLE_FILM)
        db?.execSQL(SQL_CREATE_TABLE_TRANSACTION)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DROP_TABLE_USER)
        db?.execSQL(SQL_DROP_TABLE_FILM)
        db?.execSQL(SQL_DROP_TABLE_TRANSACTION)
        db?.execSQL(SQL_CREATE_TABLE_USER)
        db?.execSQL(SQL_CREATE_TABLE_FILM)
        db?.execSQL(SQL_CREATE_TABLE_TRANSACTION)
    }
}
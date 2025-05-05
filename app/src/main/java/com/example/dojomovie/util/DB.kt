package com.example.dojomovie.util

import android.content.Context
import com.example.dojomovie.model.User

class DB {
    companion object{
        var userList = mutableListOf<User>()

        var HAS_SYNC = false

        fun syncData(ctx: Context){
            if(HAS_SYNC) return

            var helper = Helper(ctx)
            var db = helper.readableDatabase
            var cursor = db.rawQuery("SELECT * FROM user", null)

            userList.clear()
            while (cursor.moveToNext()){
                var id = cursor.getInt(0)
                var phoneNumber = cursor.getString(1)
                var password = cursor.getString(2)
                var temp = User(id, phoneNumber, password)
                userList.add(temp)
            }
            cursor.close()

            HAS_SYNC = true

        }

        fun insertNewUser(ctx: Context, phoneNumber: String, password: String){
            // generate id
            var id = 1
            if(userList.size >= 1){
                userList.last().id + 1
            }
            var temp = User(id, phoneNumber, password)
            userList.add(temp)

            // insert in SQLite
            var helper = Helper(ctx)
            var db = helper.writableDatabase
            db.execSQL("INSERT INTO user (phone_number, password)" +
                    "VALUES ('"+phoneNumber+"', '"+password+"')"
            )
        }

        var LOGGED_IN_USER: User? = null
        var REGISTERED_USER: User? = null

        fun login(phoneNumber: String, password: String){
            for(user in userList){
                if(user.phoneNumber == phoneNumber && user.password == password){
                    LOGGED_IN_USER = user
                    return
                }
            }
        }

        fun register(phoneNumber: String, password: String){
            for(user in userList){
                if(user.phoneNumber == phoneNumber){
                    return
                }
            }
            REGISTERED_USER = User(0, phoneNumber, password)
        }

        fun signOut(){
            REGISTERED_USER = null
            LOGGED_IN_USER = null
        }

    }
}
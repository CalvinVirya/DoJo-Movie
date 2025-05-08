package com.example.dojomovie.util

import android.content.Context
import com.example.dojomovie.model.Film
import com.example.dojomovie.model.Transaction
import com.example.dojomovie.model.User

class DB {
    companion object{
        var userList = mutableListOf<User>()
        var filmList = mutableListOf<Film>()
        var transactionList = mutableListOf<Transaction>()

        var HAS_SYNC = false

        fun syncData(ctx: Context){
            if(HAS_SYNC) return

            var helper = Helper(ctx)
            var db = helper.readableDatabase
            var cursorUser = db.rawQuery("SELECT * FROM user", null)
            var cursorFilm = db.rawQuery("SELECT * FROM film", null)
            var cursorTransaction = db.rawQuery("SELECT * FROM transactions", null)

            userList.clear()
            while (cursorUser.moveToNext()){
                var id = cursorUser.getInt(0)
                var phoneNumber = cursorUser.getString(1)
                var password = cursorUser.getString(2)
                var temp = User(id, phoneNumber, password)
                userList.add(temp)
            }
            cursorUser.close()

            filmList.clear()
            while (cursorFilm.moveToNext()){
                var id = cursorFilm.getString(0)
                var title = cursorFilm.getString(1)
                var image = cursorFilm.getString(2)
                var price = cursorFilm.getInt(3)
                var temp = Film(id, title, image, price)
                filmList.add(temp)
            }
            cursorFilm.close()

            transactionList.clear()
            while (cursorTransaction.moveToNext()){
                var id = cursorTransaction.getInt(0)
                var userId = cursorTransaction.getInt(1)
                var filmId = cursorTransaction.getString(2)
                var quantity = cursorTransaction.getInt(3)
                var temp = Transaction(id, userId, filmId, quantity)
                transactionList.add(temp)
            }
            cursorTransaction.close()

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

        fun insertNewFilm(ctx: Context, id: String, title: String, image: String, price: Int){
            var temp = Film(id, title, image, price)
            filmList.add(temp)

            // insert in SQLite
            var helper = Helper(ctx)
            var db = helper.writableDatabase
            db.execSQL("INSERT INTO film (film_id, film_title, film_image, film_price)" +
                    "VALUES ('"+id+"', '"+title+"', '"+image+"', '"+price+"')"
            )
        }

        fun insertNewTransaction(ctx: Context, userId: Int, filmId: String, quantity: Int){
            // generate id
            var id = 1
            if(userList.size >= 1){
                userList.last().id + 1
            }
            var temp = Transaction(id, userId, filmId, quantity)
            transactionList.add(temp)

            // insert in SQLite
            var helper = Helper(ctx)
            var db = helper.writableDatabase
            db.execSQL("INSERT INTO transactions (user_id, film_id, quantity)" +
                    "VALUES ('"+userId+"', '"+filmId+"', '"+quantity+"')"
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

        fun getTransactionHistory(context: Context, userId: Int): MutableList<Transaction>{
            val transactions = mutableListOf<Transaction>()
            val helper = Helper(context)
            val db = helper.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM transactions WHERE user_id LIKE ${userId}", null)

            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(0)
                    val userId = cursor.getInt(1)
                    val filmId = cursor.getString(2)
                    val quantity = cursor.getInt(3)
                    transactions.add(Transaction(id, userId, filmId, quantity))
                } while (cursor.moveToNext())
            }

            cursor.close()
            db.close()
            return transactions
        }

        fun getFilmDetail(filmId: String): Film? {
            for(film in filmList){
                if(film.id == filmId){
                    return film
                }
            }
            return null
        }

    }
}
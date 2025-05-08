package com.example.dojomovie.model

data class Transaction(
    var id : Int,
    var userId : Int,
    var filmId : String,
    var quantity : Int
)

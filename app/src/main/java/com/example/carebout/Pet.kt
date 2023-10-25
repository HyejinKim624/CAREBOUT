package com.example.carebout

import java.util.Date

data class Pet(
    val name : String,
    val birth : Date,
    val spec : String,
    val isMan : Boolean,
    val weightArrList : ArrayList<Float>
)

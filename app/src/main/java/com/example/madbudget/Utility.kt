package com.example.madbudget

import android.util.Log
import kotlin.math.min

class Utility{
    companion object{

        //https://stackoverflow.com/a/35243421
        fun bigLog(tag: String, message: String){
            val chunkSize = 2048
            for(i in 0..message.length step chunkSize){
                Log.i(tag, message.substring(i, min(message.length, i + chunkSize)))
            }
        }
    }
}
package com.gruppe17.madbudget

import android.util.Log
import com.beust.klaxon.Klaxon
import java.lang.Exception
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

        inline fun <reified T> parse(msg: String): T? {
            return try {
                Klaxon().parse<T>(msg)
            } catch (e: Exception) {
                null
            }
        }

        inline fun <reified T> parseArray(msg: String): List<T>? {
            return try {
                Klaxon().parseArray<T>(msg)
            } catch (e: Exception) {
                null
            }
        }
    }
}
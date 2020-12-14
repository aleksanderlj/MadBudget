package com.gruppe17.madbudget

import android.content.Context
import android.util.Log
import com.beust.klaxon.Klaxon
import com.gruppe17.madbudget.database.AppDatabase
import com.gruppe17.madbudget.models.Ingredient
import com.gruppe17.madbudget.rest.coop.model.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import java.lang.Exception
import kotlin.math.min

object Utility {
    //https://stackoverflow.com/a/35243421
    fun bigLog(tag: String, message: String) {
        val chunkSize = 2048
        for (i in 0..message.length step chunkSize) {
            Log.i(tag, message.substring(i, min(message.length, i + chunkSize)))
        }
    }

    inline fun <reified T> parse(msg: String): T? {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val coopProductAdapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        return coopProductAdapter.fromJson(msg)
    }

    inline fun <reified T> parseArray(msg: String): List<T>? {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val listCoopProductType = Types.newParameterizedType(List::class.java, T::class.java)
        val coopProductAdapter: JsonAdapter<List<T>> = moshi.adapter(listCoopProductType)

        return coopProductAdapter.fromJson(msg)
    }

    fun readFileAsString(fileId : Int, context: Context) : String{
        val input = context.resources.openRawResource(fileId)
        val s = input.bufferedReader().use { it.readText() }
        return s
    }

    /*
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

     */

    fun deleteRecipe(id: Int, db: AppDatabase){
        val ingSel = db.ingredientSelectionDao().getAllByRecipeId(id)

        for(n in ingSel){
            db.ingredientDao().deleteByParentId(n.ingredientSelection.id)
            db.ingredientSelectionDao().delete(n.ingredientSelection)
        }

        db.recipeDao().deleteById(id)

    }

    fun getTestCoopStoreList(): CoopStoreList {
        val stores = ArrayList<CoopStore>()
        stores.add(CoopStore(1002, "SuperBrugsen", "SuperBrugsen Finsensvej", "Finsensvej 14B", 2000, "Frederiksberg", 50, CoopLocation(listOf(12.5191908, 55.68143)), listOf(CoopOpeningHour("08.00 - 21.45", "torsdag", 8.0, 21.45, "2020-11-12T08:00:00", "2020-11-12T21:45:00"))))
        stores.add(CoopStore(1146, "SuperBrugsen", "SuperBrugsen Herstedvester", "Egelundsvej 20", 2620, "Albertslund", 22, CoopLocation(listOf(12.3391533, 55.6704674)), listOf(CoopOpeningHour("08.00 - 20.00", "torsdag", 8.0, 20.0, "2020-11-12T08:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(1420, "SuperBrugsen", "SuperBrugsen Ålekistevej", "Ålekistevej 75", 2720, "Vanløse", 278, CoopLocation(listOf(12.4837465, 55.68258)), listOf(CoopOpeningHour("08.00 - 21.00", "torsdag", 8.0, 21.0, "2020-11-12T08:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(1760, "SuperBrugsen", "SuperBrugsen Brønshøj Torv", "Frederikssundsvej 175", 2700, "Brønshøj", 54, CoopLocation(listOf(12.4995937, 55.70426)), listOf(CoopOpeningHour("08.00 - 21.00", "torsdag", 8.0, 21.0, "2020-11-12T08:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(1885, "Dagli'Brugsen", "Dagli'Brugsen Skyttegården", "Vigerslevvej 125", 2500, "Valby", 688, CoopLocation(listOf(12.4834814, 55.6618347)), listOf(CoopOpeningHour("08.00 - 20.00", "torsdag", 8.0, 20.0, "2020-11-12T08:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(8345, "SuperBrugsen", "SuperBrugsen Gentofte Bymidte", "Baunegårdsvej 7 F", 2820, "Gentofte", 89, CoopLocation(listOf(12.5423288, 55.7467041)), listOf(CoopOpeningHour("08.00 - 20.45", "torsdag", 8.0, 20.45, "2020-11-12T08:00:00", "2020-11-12T20:45:00"))))
        stores.add(CoopStore(8365, "SuperBrugsen", "SuperBrugsen Vangede", "Vangede Bygade 61", 2820, "Gentofte", 109, CoopLocation(listOf(12.51849, 55.75011)), listOf(CoopOpeningHour("08.00 - 20.45", "torsdag", 8.0, 20.45, "2020-11-12T08:00:00", "2020-11-12T20:45:00"))))
        stores.add(CoopStore(8449, "SuperBrugsen", "SuperBrugsen Skovlunde", "Bybjergvej 2", 2740, "Skovlunde", 229, CoopLocation(listOf(12.4016857, 55.7192345)), listOf(CoopOpeningHour("08.00 - 20.45", "torsdag", 8.0, 20.45, "2020-11-12T08:00:00", "2020-11-12T20:45:00"))))
        stores.add(CoopStore(8496, "SuperBrugsen", "SuperBrugsen Fortunbyen", "Lyngbygårdsvej 151", 2800, "Kongens Lyngby", 11, CoopLocation(listOf(12.5214319, 55.7757759)), listOf(CoopOpeningHour("07.00 - 21.00", "torsdag", 7.0, 21.0, "2020-11-12T07:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(8715, "Kvickly", "Kvickly Farum", "Farum Bytorv 2", 3520, "Farum", 115, CoopLocation(listOf(12.3790293, 55.814003)), listOf(CoopOpeningHour("07.00 - 21.00", "torsdag", 7.0, 21.0, "2020-11-12T07:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(8886, "SuperBrugsen", "SuperBrugsen Smørum", "Flodvej 75 G", 2765, "Smørum", 82, CoopLocation(listOf(12.3059969, 55.7326965)), listOf(CoopOpeningHour("08.00 - 20.45", "torsdag", 8.0, 20.45, "2020-11-12T08:00:00", "2020-11-12T20:45:00"))))
        stores.add(CoopStore(23015, "Irma", "Irma Sorgenfri Torv", "Sorgenfri Torv 14", 2830, "Virum", 772, CoopLocation(listOf(12.4811907, 55.7818069)), listOf(CoopOpeningHour("08.00 - 20.00", "torsdag", 8.0, 20.0, "2020-11-12T08:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(23019, "Irma", "Irma Godthåbsvej", "Godthåbsvej 197", 2720, "Vanløse", 781, CoopLocation(listOf(12.51352, 55.6923141)), listOf(CoopOpeningHour("09.00 - 20.00", "torsdag", 9.0, 20.0, "2020-11-12T09:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(23024, "Irma", "Irma Holte", "Holte Stationsvej 6", 2840, "Holte", 782, CoopLocation(listOf(12.47181, 55.81104)), listOf(CoopOpeningHour("09.00 - 20.00", "torsdag", 9.0, 20.0, "2020-11-12T09:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(23026, "Irma", "Irma Søborg Hovedgade", "Søborg Hovedgade 70", 2860, "Søborg", 465, CoopLocation(listOf(12.5159979, 55.7336655)), listOf(CoopOpeningHour("09.00 - 20.00", "torsdag", 9.0, 20.0, "2020-11-12T09:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(23033, "Irma", "Irma Islev Station", "Tybjergvej 115-117", 2720, "Vanløse", 485, CoopLocation(listOf(12.4698334, 55.6993523)), listOf(CoopOpeningHour("09.00 - 20.00", "torsdag", 9.0, 20.0, "2020-11-12T09:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(23034, "Irma", "Irma Ndr. Fasanvej", "Nordre Fasanvej 175", 2000, "Frederiksberg", 698, CoopLocation(listOf(12.5321741, 55.69336)), listOf(CoopOpeningHour("08.00 - 21.00", "torsdag", 8.0, 21.0, "2020-11-12T08:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(23060, "Irma", "Irma Vanløse", "Jernbane Allé 53", 2720, "Vanløse", 667, CoopLocation(listOf(12.4889545, 55.6888771)), listOf(CoopOpeningHour("09.00 - 21.00", "torsdag", 9.0, 21.0, "2020-11-12T09:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(23098, "Irma", "Irma Smedetoften", "Smedetoften 10", 2400, "København Nv", 729, CoopLocation(listOf(12.5214777, 55.70915)), listOf(CoopOpeningHour("09.00 - 20.00", "torsdag", 9.0, 20.0, "2020-11-12T09:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(23104, "Irma", "Irma Roskildevej", "Roskildevej 148 D", 2500, "Valby", 788, CoopLocation(listOf(12.4879789, 55.67276)), listOf(CoopOpeningHour("08.00 - 20.00", "torsdag", 8.0, 20.0, "2020-11-12T08:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(23106, "Irma", "Irma Stengårds Allé", "Stengårds Alle 38A", 2800, "Kongens Lyngby", 334, CoopLocation(listOf(12.48402, 55.76037)), listOf(CoopOpeningHour("09.00 - 21.00", "torsdag", 9.0, 21.0, "2020-11-12T09:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(23116, "Irma", "Irma Emdrupvej 155", "Emdrupvej 155", 2400, "København Nv", 768, CoopLocation(listOf(12.5321655, 55.72286)), listOf(CoopOpeningHour("09.00 - 20.00", "torsdag", 9.0, 20.0, "2020-11-12T09:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(23119, "Irma", "Irma Lyngby Hovedgade", "Lyngby Hovedgade 21", 2800, "Kongens Lyngby", 668, CoopLocation(listOf(12.5018559, 55.7719269)), listOf(CoopOpeningHour("08.00 - 20.00", "torsdag", 8.0, 20.0, "2020-11-12T08:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(23128, "Irma", "Irma Bagsværd Hovedgade", "Bagsværd Hovedgade 107C", 2880, "Bagsværd", 148, CoopLocation(listOf(12.4554424, 55.7601357)), listOf(CoopOpeningHour("09.00 - 20.00", "torsdag", 9.0, 20.0, "2020-11-12T09:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(23131, "Irma", "Irma Finsensvej", "Finsensvej 9", 2000, "Frederiksberg", 825, CoopLocation(listOf(12.5196285, 55.6810074)), listOf(CoopOpeningHour("08.00 - 21.00", "torsdag", 8.0, 21.0, "2020-11-12T08:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(23149, "Irma", "Irma Farum", "Farum Bytorv 72", 3520, "Farum", 786, CoopLocation(listOf(12.381362, 55.8144264)), listOf(CoopOpeningHour("08.00 - 20.00", "torsdag", 8.0, 20.0, "2020-11-12T08:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(24307, "Fakta", "fakta Brøndby, Kærdammen", "Kærdammen 1 A", 2605, "Brøndby", 2307, CoopLocation(listOf(12.43053, 55.6586227)), listOf(CoopOpeningHour("08.00 - 21.00", "torsdag", 8.0, 21.0, "2020-11-12T08:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(24312, "Fakta", "fakta Kbh Nv Frederikssundsvej", "Frederikssundsvej 44", 2400, "København Nv", 2312, CoopLocation(listOf(12.5316133, 55.70266)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24314, "Fakta", "fakta KBH N, Lundtoftegade", "Lundtoftegade 1", 2200, "København N", 2314, CoopLocation(listOf(12.5382786, 55.691864)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24327, "Fakta", "fakta Rødovre, Fortvej", "Fortvej 9", 2610, "Rødovre", 2327, CoopLocation(listOf(12.4585171, 55.6958847)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24338, "Fakta", "fakta Farum", "Farum Bytorv 63", 3520, "Farum", 2338, CoopLocation(listOf(12.3816128, 55.814888)), listOf(CoopOpeningHour("08.00 - 20.00", "torsdag", 8.0, 20.0, "2020-11-12T08:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(24339, "Fakta", "fakta Gentofte Vangede Bygade", "Vangede Bygade 40", 2820, "Gentofte", 2339, CoopLocation(listOf(12.5182953, 55.7514343)), listOf(CoopOpeningHour("08.00 - 21.00", "torsdag", 8.0, 21.0, "2020-11-12T08:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(24340, "Fakta", "fakta Kbh N Hamletsgade", "Hamletsgade 8", 2200, "København N", 2340, CoopLocation(listOf(12.5462961, 55.7022438)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24351, "Fakta", "fakta Virum", "Virum Torv 5", 2830, "Virum", 2351, CoopLocation(listOf(12.4722452, 55.7950745)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24365, "Fakta", "fakta Lyngby Station", "Jernbanepladsen 49", 2800, "Kongens Lyngby", 2365, CoopLocation(listOf(12.50249, 55.7687874)), listOf(CoopOpeningHour("07.00 - 22.00", "torsdag", 7.0, 22.0, "2020-11-12T07:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24366, "Fakta", "fakta Vanløse Ålekistevej", "Ålekistevej 47", 2720, "Vanløse", 2366, CoopLocation(listOf(12.4844112, 55.68033)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24375, "Fakta", "fakta Brønshøj, Frederikssundsvej", "Frederikssundsvej 123", 2700, "Brønshøj", 2375, CoopLocation(listOf(12.5106544, 55.70584)), listOf(CoopOpeningHour("08.00 - 20.00", "torsdag", 8.0, 20.0, "2020-11-12T08:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(24303, "Fakta", "fakta Bagsværd", "Krogshøjvej 1", 2880, "Bagsværd", 2303, CoopLocation(listOf(12.4520721, 55.76005)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24406, "Fakta", "fakta Herlev, Vindebyvej", "Vindebyvej 25", 2730, "Herlev", 2406, CoopLocation(listOf(12.4317265, 55.727478)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24411, "Fakta", "fakta Glostrup, Trippendal Centr.", "Tjørnehusene 1", 2600, "Glostrup", 2411, CoopLocation(listOf(12.3726206, 55.6738853)), listOf(CoopOpeningHour("08.00 - 21.00", "torsdag", 8.0, 21.0, "2020-11-12T08:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(24413, "Fakta", "fakta Smørum", "Flodvej 75 A", 2765, "Smørum", 2413, CoopLocation(listOf(12.30514, 55.73283)), listOf(CoopOpeningHour("08.00 - 21.00", "torsdag", 8.0, 21.0, "2020-11-12T08:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(24434, "Fakta", "fakta Frederiksberg,Finsensvej", "Finsensvej 47A", 2000, "Frederiksberg", 2434, CoopLocation(listOf(12.5079594, 55.68187)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24431, "Fakta", "fakta Herlev, Dildhaven", "Dildhaven 47-49", 2730, "Herlev", 2431, CoopLocation(listOf(12.4225683, 55.7426033)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24356, "Fakta", "fakta Frb, Ndr Fasanvej", "Nordre Fasanvej 117", 2000, "Frederiksberg", 2356, CoopLocation(listOf(12.5297155, 55.69047)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24399, "Fakta", "fakta Rødovre, Valhøjs Alle", "Valhøjs Alle 67A", 2610, "Rødovre", 2399, CoopLocation(listOf(12.4538975, 55.6728745)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24394, "Fakta", "Coop 365 Søborg Hovedgade Søborg", "Søborg Hovedgade 66", 2860, "Søborg", 2394, CoopLocation(listOf(12.5163813, 55.7333679)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24450, "Fakta", "fakta Vanløse, Sallingvej", "Sallingvej 60", 2720, "Vanløse", 2450, CoopLocation(listOf(12.5002356, 55.6929474)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(8240, "Kvickly", "Kvickly Værløse", "Bymidten 75", 3500, "Værløse", 84, CoopLocation(listOf(12.3758106, 55.78312)), listOf(CoopOpeningHour("07.00 - 21.00", "torsdag", 7.0, 21.0, "2020-11-12T07:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(24438, "Fakta", "fakta Gladsaxe", "Gyngemose Parkvej 76", 2860, "Søborg", 2438, CoopLocation(listOf(12.4770308, 55.7282867)), listOf(CoopOpeningHour("08.00 - 21.00", "torsdag", 8.0, 21.0, "2020-11-12T08:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(24446, "Fakta", "fakta Herlev", "Herlev Torv 28B", 2730, "Herlev", 2446, CoopLocation(listOf(12.4392033, 55.72474)), listOf(CoopOpeningHour("08.00 - 20.00", "torsdag", 8.0, 20.0, "2020-11-12T08:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(24468, "Fakta", "fakta Værløse", "Bymidten 39 A", 3500, "Værløse", 2468, CoopLocation(listOf(12.3739824, 55.7834435)), listOf(CoopOpeningHour("07.00 - 22.00", "torsdag", 7.0, 22.0, "2020-11-12T07:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(1710, "Kvickly", "Kvickly Brøndby", "Nygårds Plads 29", 2605, "Brøndby", 968, CoopLocation(listOf(12.4383717, 55.6676826)), listOf(CoopOpeningHour("08.00 - 21.00", "torsdag", 8.0, 21.0, "2020-11-12T08:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(24472, "Fakta", "fakta Godthåbsparken", "Rypehusene 3", 2620, "Albertslund", 2472, CoopLocation(listOf(12.3543911, 55.67523)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(8025, "Dagli'Brugsen", "Dagli'Brugsen Parcelvej", "Parcelvej 71", 2830, "Virum", 965, CoopLocation(listOf(12.4534216, 55.7993279)), listOf(CoopOpeningHour("08.00 - 20.00", "torsdag", 8.0, 20.0, "2020-11-12T08:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(24475, "Fakta", "fakta Frederiksberg, Peter Bangsvej", "Peter Bangs Vej 24-28", 2000, "Frederiksberg", 2475, CoopLocation(listOf(12.516058, 55.6791649)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24492, "Fakta", "fakta Rødovre, Islevbrovej", "Islevbrovej 39", 2610, "Rødovre", 2492, CoopLocation(listOf(12.44307, 55.7046738)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24488, "Fakta", "fakta Måløv", "Østerhøj Bygade 2", 2750, "Ballerup", 2488, CoopLocation(listOf(12.335762, 55.75021)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24483, "Fakta", "fakta Frederiksborgvej", "Frederiksborgvej 73", 2400, "København Nv", 2483, CoopLocation(listOf(12.531455, 55.70767)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24557, "Fakta", "fakta Rødovrevej", "Rødovrevej 210", 2610, "Rødovre", 2557, CoopLocation(listOf(12.4647274, 55.6879272)), listOf(CoopOpeningHour("07.00 - 22.00", "torsdag", 7.0, 22.0, "2020-11-12T07:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24538, "Fakta", "fakta Søholm", "Frederikssundsvej 202", 2700, "Brønshøj", 2538, CoopLocation(listOf(12.491106, 55.7061)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24520, "Fakta", "fakta Buddinge", "Søborg Hovedgade 219", 2860, "Søborg", 2520, CoopLocation(listOf(12.494936, 55.7420464)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(23084, "Irma", "Irma Peter Bangs Vej", "Peter Bangs Vej 61", 2000, "Frederiksberg", 93, CoopLocation(listOf(12.507781, 55.6786423)), listOf(CoopOpeningHour("08.00 - 21.00", "torsdag", 8.0, 21.0, "2020-11-12T08:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(1175, "SuperBrugsen", "SuperBrugsen Godthåbsvej", "Godthåbsvej 207", 2720, "Vanløse", 239, CoopLocation(listOf(12.5120573, 55.693058)), listOf(CoopOpeningHour("08.00 - 21.45", "torsdag", 8.0, 21.45, "2020-11-12T08:00:00", "2020-11-12T21:45:00"))))
        stores.add(CoopStore(9923, "COOP MAD", "Aula Brugsen", "Roskildevej 65", 2620, "Albertslund", 38, CoopLocation(listOf(12.3608637, 55.6613)), listOf(CoopOpeningHour("08.00 - 17.00", "torsdag", 8.0, 17.0, "2020-11-12T08:00:00", "2020-11-12T17:00:00"))))
        stores.add(CoopStore(1135, "Dagli'Brugsen", "Daglibrugsen Herman Bangs Plads", "Herman Bangs Plads 3", 2500, "Valby", 404, CoopLocation(listOf(12.50321, 55.6673737)), listOf(CoopOpeningHour("08.00 - 21.00", "torsdag", 8.0, 21.0, "2020-11-12T08:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(23126, "Irma", "Irma Rødovre Centrum", "Rødovre Centrum 1G, St 150", 2610, "Rødovre", 600, CoopLocation(listOf(12.4568071, 55.67904)), listOf(CoopOpeningHour("08.00 - 20.00", "torsdag", 8.0, 20.0, "2020-11-12T08:00:00", "2020-11-12T20:00:00"))))
        stores.add(CoopStore(24349, "Fakta", "fakta Glostrup", "Diget 60-62", 2600, "Glostrup", 2349, CoopLocation(listOf(12.3872452, 55.6670074)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(24385, "Fakta", "fakta Vallensbæk, Tværbækvej", "Tværbækvej 3", 2625, "Vallensbæk", 2385, CoopLocation(listOf(12.3576345, 55.6474152)), listOf(CoopOpeningHour("07.00 - 21.00", "torsdag", 7.0, 21.0, "2020-11-12T07:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(24457, "Fakta", "fakta Brønshøj", "Frederikssundsvej 324A", 2700, "Brønshøj", 2457, CoopLocation(listOf(12.4716368, 55.7122459)), listOf(CoopOpeningHour("08.00 - 22.00", "torsdag", 8.0, 22.0, "2020-11-12T08:00:00", "2020-11-12T22:00:00"))))
        stores.add(CoopStore(8550, "Kvickly", "Kvickly Buddinge", "Buddingevej 272", 2860, "Søborg", 94, CoopLocation(listOf(12.4942093, 55.74663)), listOf(CoopOpeningHour("07.00 - 21.00", "torsdag", 7.0, 21.0, "2020-11-12T07:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(1910, "Kvickly", "Kvickly Albertslund", "Stationsporten 8", 2620, "Albertslund", 51, CoopLocation(listOf(12.3524456, 55.65645)), listOf(CoopOpeningHour("07.00 - 21.00", "torsdag", 7.0, 21.0, "2020-11-12T07:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(1060, "SuperBrugsen", "SuperBrugsen Brøndby", "Søndre Ringvej 45 A", 2605, "Brøndby", 81, CoopLocation(listOf(12.3961382, 55.6551132)), listOf(CoopOpeningHour("08.00 - 21.00", "torsdag", 8.0, 21.0, "2020-11-12T08:00:00", "2020-11-12T21:00:00"))))
        stores.add(CoopStore(9920, "COOP MAD", "Coop.dk MAD", "Jydekrogen 4", 2625, "Vallensbæk", 1112, CoopLocation(listOf(12.3761034, 55.64588)), listOf(CoopOpeningHour("00.30 - 24.00", "torsdag", 0.3, 24.0, "2020-11-12T00:30:00", "2020-11-12T23:59:59"))))

        return CoopStoreList(1, 1, 1000, 73, stores, 1, "")
    }

    /*
    fun getTestIngredientList(): ArrayList<Ingredient> {
        val l = ArrayList<Ingredient>()
        l.add(
            Ingredient(
                0,
                "FLÆSKESTEG 1,2-2,4 KG MED SVÆR",
                1799.9999999999998,
                "G",
                null,
                null,
                false,
                49.9,
                0
            )
        )
        l.add(Ingredient(0, "MÖRT GRYDESTEG 800-1600G", 1200.0, "G", null, null, false, 79.9, 0))
        l.add(Ingredient(0, "HAKKET OKSEKØD 8-12% 450G", 450.0, "G", null, null, false, 30.0, 0))
        l.add(
            Ingredient(
                0,
                "MÖRT TYKSTEGSBØFFER 300 G, UNGKVÆG",
                300.0,
                "G",
                null,
                null,
                false,
                55.0,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "HAKKET GRISE-& KALVEKØD 8-12% 500G",
                500.0,
                "G",
                null,
                null,
                false,
                38.5,
                0
            )
        )
        l.add(Ingredient(0, "HAKKET OKSEKØD 4-7% 400G ", 400.0, "G", null, null, false, 35.0, 0))
        l.add(Ingredient(0, "KOTELETTER 500G ", 500.0, "G", null, null, false, 43.95, 0))
        l.add(Ingredient(0, "STEGEFLÆSK GRIS 400G", 400.0, "G", null, null, false, 39.95, 0))
        l.add(Ingredient(0, "JULEMEDISTER 500G ", 500.0, "G", null, null, false, 32.95, 0))
        l.add(Ingredient(0, "BRONTOSAURUS STEAK 400 G", 400.0, "G", null, null, false, 59.0, 0))
        l.add(Ingredient(0, "HAKKET GRISEKØD 8-12% 500G", 500.0, "G", null, null, false, 35.0, 0))
        l.add(Ingredient(0, "LÅRMIX 800G ", 800.0, "G", null, null, false, 33.0, 0))
        l.add(Ingredient(0, "KYLLINGE INDERFILET 290 G", 290.0, "G", null, null, false, 27.5, 0))
        l.add(Ingredient(0, "KYLLINGEBRYST 13 % 450 G", 450.0, "G", null, null, false, 29.75, 0))
        l.add(Ingredient(0, "BRYSTFILET 280 GRAM ", 280.0, "GRAM", null, null, false, 27.5, 0))
        l.add(Ingredient(0, "HEL KYLLING 1350G ", 1350.0, "G", null, null, false, 49.95, 0))
        l.add(
            Ingredient(
                0,
                "COOP BRYSTFILET 13 %  800 GRAM ",
                800.0,
                "GRAM",
                null,
                null,
                false,
                49.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "SALAMI LAV FEDT 110 G - 3 STJERNET",
                110.0,
                "G",
                null,
                null,
                false,
                22.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "JÆGERPØLSE 9% FEDT 110 G. - 3 STJERNET",
                110.0,
                "G",
                null,
                null,
                false,
                16.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "COOP SØNDERJYSK SPEGEPØLSE 300 G",
                300.0,
                "G",
                null,
                null,
                false,
                30.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "COOP SPEGEPØLSE M/HVIDLØG 300 G",
                300.0,
                "G",
                null,
                null,
                false,
                30.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "COOP KYLLINGSTYKKER STEGT 150 G",
                150.0,
                "G",
                null,
                null,
                false,
                18.75,
                0
            )
        )
        l.add(Ingredient(0, "X-TRA SALAMI 200 G", 200.0, "G", null, null, false, 11.5, 0))
        l.add(
            Ingredient(
                0,
                "COOP KRYDERET KYLLINGE FILET 150 G",
                150.0,
                "G",
                null,
                null,
                false,
                20.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "COOP SPEGEPØLSE M/PEBER 300 G",
                300.0,
                "G",
                null,
                null,
                false,
                30.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "TYNDTSKÅRET RØGET SKINKE 100 G.",
                100.0,
                "G",
                null,
                null,
                false,
                18.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "TYNDTSKÅRET STEGT KYLLING 80 G.",
                80.0,
                "G",
                null,
                null,
                false,
                18.95,
                0
            )
        )
        l.add(Ingredient(0, "STRYHNS FRANSK POSTEJ 200 G", 200.0, "G", null, null, false, 18.5, 0))
        l.add(
            Ingredient(
                0,
                "STRYHNS GROVHAKKET LEVERPOSTEJ 200 G",
                200.0,
                "G",
                null,
                null,
                false,
                18.5,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "STRYHNS BACON-PEBER POSTEJ 300 G",
                300.0,
                "G",
                null,
                null,
                false,
                26.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "STRYHNS GROVHAKKET LEVERPOSTEJ 450 G",
                450.0,
                "G",
                null,
                null,
                false,
                31.5,
                0
            )
        )
        l.add(Ingredient(0, "STRYHNS RØGET MEDISTER 345 G", 345.0, "G", null, null, false, 27.5, 0))
        l.add(
            Ingredient(
                0,
                "GRILL-PØLSER 350 G. - LANGELÆNDER",
                350.0,
                "G",
                null,
                null,
                false,
                36.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "WIENER-PØLSER 330 G. - LANGELÆNDER",
                330.0,
                "G",
                null,
                null,
                false,
                36.95,
                0
            )
        )
        l.add(Ingredient(0, "JULEPOSTEJ MED BACON 450 G.", 450.0, "G", null, null, false, 30.5, 0))
        l.add(Ingredient(0, "SYLTE 400 G. - STRYHNS", 400.0, "G", null, null, false, 34.95, 0))
        l.add(
            Ingredient(
                0,
                "KØDPØLSE, SKIVER 125 G. -3-STJERNET",
                125.0,
                "G",
                null,
                null,
                false,
                16.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "SØNDERJYSK SPEGEPØLSE 125 G. - 3-STJERNET",
                125.0,
                "G",
                null,
                null,
                false,
                22.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "KARTOFFELSPEGEPØLSE 125 G. - 3-STJERNET",
                125.0,
                "G",
                null,
                null,
                false,
                22.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "GOURMET VINTERPØLSE 400 G M/SPINAT OG PEBER",
                400.0,
                "G",
                null,
                null,
                false,
                45.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "3-STJERNET SALAMI ORIGINAL 175 G",
                175.0,
                "G",
                null,
                null,
                false,
                21.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "ØKOLOGISK MILD SPEGEPØLSE 200 G.",
                200.0,
                "G",
                null,
                null,
                false,
                42.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "HAMBURGERRYG 90 G. - PÅLÆKKER",
                90.0,
                "G",
                null,
                null,
                false,
                16.95,
                0
            )
        )
        l.add(Ingredient(0, "TULIP BACON I SKIVER 3 X 125 G", 375.0, "G", 3, null, false, 39.95, 0))
        l.add(
            Ingredient(
                0,
                "BRUNCH PØLSER 300 G, STEFF HOULBERG",
                300.0,
                "G",
                null,
                null,
                false,
                32.95,
                0
            )
        )
        l.add(Ingredient(0, "RULLEPØLSE 80 G. - PÅLÆKKER", 80.0, "G", null, null, false, 16.95, 0))
        l.add(Ingredient(0, "GØL WIENERPØLSER 440 G", 440.0, "G", null, null, false, 40.95, 0))
        l.add(Ingredient(0, "GØL HOT DOG PØLSER 440 G", 440.0, "G", null, null, false, 40.95, 0))
        l.add(Ingredient(0, "GØL FRANKFURTER 500 G", 500.0, "G", null, null, false, 40.95, 0))
        l.add(
            Ingredient(
                0,
                "PÅLÆKKER BACON LEVERPOSTEJ 200 G",
                200.0,
                "G",
                null,
                null,
                false,
                16.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "PÅLÆKKER SØNDERJYSK SPEGEPØLSE 125 G",
                125.0,
                "G",
                null,
                null,
                false,
                17.95,
                0
            )
        )
        l.add(Ingredient(0, "GØL GRILLPØLSER 420 G", 420.0, "G", null, null, false, 40.95, 0))
        l.add(Ingredient(0, "KALKUNSALAMI 80 G", 80.0, "G", null, null, false, 16.95, 0))
        l.add(
            Ingredient(
                0,
                "KYLLINGE WIENERPØLSER 5 STK./250 G, HANEGAL, ØKO.",
                250.0,
                "G",
                5,
                null,
                false,
                41.5,
                0
            )
        )
        l.add(Ingredient(0, "GÅRDLYKKE ROASTBEEF 70 G.", 70.0, "G", null, null, false, 20.95, 0))
        l.add(Ingredient(0, "GÅRDLYKKE RULLEPØLSE 90 G.", 90.0, "G", null, null, false, 20.95, 0))
        l.add(Ingredient(0, "GÅRDLYKKE KAMSTEG 90 G", 90.0, "G", null, null, false, 20.95, 0))
        l.add(Ingredient(0, "X-TRA KOGT SKINKE 150 G", 150.0, "G", null, null, false, 8.95, 0))
        l.add(Ingredient(0, "X-TRA BACON TERN 200 G", 200.0, "G", null, null, false, 13.25, 0))
        l.add(Ingredient(0, "X-TRA SKINKE STRIMLER 200 G", 200.0, "G", null, null, false, 14.0, 0))
        l.add(Ingredient(0, "X-TRA COCKTAIL PØLSER 200 G", 200.0, "G", null, null, false, 14.75, 0))
        l.add(
            Ingredient(
                0,
                "X-TRA GRILL PØLSER 550 G 10 STK",
                550.0,
                "G",
                10,
                null,
                false,
                23.75,
                0
            )
        )
        l.add(Ingredient(0, "XTRA HOTDOG PØLSER 500 G 8 STK", 500.0, "G", 8, null, false, 26.25, 0))
        l.add(Ingredient(0, "X-TRA LEVERPOSTEJ 500 G", 500.0, "G", null, null, false, 9.95, 0))
        l.add(
            Ingredient(
                0,
                "X-TRA PARTY PØLSER 500 G 10 STK",
                500.0,
                "G",
                10,
                null,
                false,
                22.25,
                0
            )
        )
        l.add(Ingredient(0, "X-TRA BACON 2 X 140 G", 280.0, "G", 2, null, false, 20.5, 0))
        l.add(
            Ingredient(
                0,
                "COOP PEPPERONI I SKIVER 150 G",
                150.0,
                "G",
                null,
                null,
                false,
                13.5,
                0
            )
        )
        l.add(Ingredient(0, "COOP RULLEPØLSE 100 G", 100.0, "G", null, null, false, 18.95, 0))
        l.add(Ingredient(0, "COOP HAMBURGERRYG 100 G", 100.0, "G", null, null, false, 17.5, 0))
        l.add(Ingredient(0, "COOP KOGT SKINKE 100 G", 100.0, "G", null, null, false, 18.25, 0))
        l.add(
            Ingredient(
                0,
                "KYLLINGEBRYST STEGT 90 G - COOP",
                90.0,
                "G",
                null,
                null,
                false,
                15.95,
                0
            )
        )
        l.add(Ingredient(0, "COOP ROASTBEEF 70 G.", 70.0, "G", null, null, false, 16.25, 0))
        l.add(
            Ingredient(
                0,
                "COOP BACON LEVERPOSTEJ 350 G",
                350.0,
                "G",
                null,
                null,
                false,
                13.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "SANDWICH SKIVER NATUREL 125 G. - DIT VALG",
                125.0,
                "G",
                null,
                null,
                false,
                16.95,
                0
            )
        )
        l.add(Ingredient(0, "XTRA HAMBURGERRYG 140 G", 140.0, "G", null, null, false, 12.5, 0))
        l.add(
            Ingredient(
                0,
                "X-TRA VARMRØGET LAKS MED PEBER 125 G",
                125.0,
                "G",
                null,
                null,
                false,
                30.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "TENAX FISKEFRIKADELLER 260 G 4 STK MSC",
                260.0,
                "G",
                4,
                null,
                false,
                24.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "TENAX LAKSEFRIKADELLER 260 G 4 STK",
                260.0,
                "G",
                4,
                null,
                false,
                24.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "LAUNIS MINI FISKEFRIKADELLER 180 G",
                180.0,
                "G",
                null,
                null,
                false,
                21.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "EKSTRA STORE REJER CAPELLA 400/200 G",
                200.0,
                "G",
                null,
                null,
                false,
                55.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "RØRÆG 150 G, ROYAL DANISH SEAFOOD",
                150.0,
                "G",
                null,
                null,
                false,
                13.95,
                0
            )
        )
        l.add(Ingredient(0, "VARMRØGET ØRREDFILET 125G", 125.0, "G", null, null, false, 20.75, 0))
        l.add(
            Ingredient(
                0,
                "VARMRØGET MAKREL PEBER 150 G",
                150.0,
                "G",
                null,
                null,
                false,
                21.95,
                0
            )
        )
        l.add(Ingredient(0, "HEL RØGET MAKREL 250 G", 250.0, "G", null, null, false, 22.95, 0))
        l.add(
            Ingredient(
                0,
                "HAVET'S FISKEFRIKADELLE 380 G",
                380.0,
                "G",
                null,
                null,
                false,
                32.5,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "X-TRA REJER I LAGE 175/100 G",
                100.0,
                "G",
                null,
                null,
                false,
                14.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "COOP XL REJER I LAGE 360/170 G MSC",
                170.0,
                "G",
                null,
                null,
                false,
                42.95,
                0
            )
        )
        l.add(Ingredient(0, "COOP RØGET LAKS 160 G", 160.0, "G", null, null, false, 56.95, 0))
        l.add(
            Ingredient(
                0,
                "COOP RØGET LAKS I SKIVER 100 G",
                100.0,
                "G",
                null,
                null,
                false,
                31.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "COOP GRAVAD LAKS  I SKIVER 100 G MSC",
                100.0,
                "G",
                null,
                null,
                false,
                31.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "K-S LÆKKERIER SKAGENSSALAT 250 G",
                250.0,
                "G",
                null,
                null,
                false,
                20.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "K-S MAKRELSALAT 150 G K-SALAT",
                150.0,
                "G",
                null,
                null,
                false,
                24.0,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "DILDDRESSING 300 ML BAHNCKE",
                300.0,
                "ML",
                null,
                null,
                false,
                18.25,
                0
            )
        )
        l.add(Ingredient(0, "GRÅSTEN HØNSE SALAT 300 G", 300.0, "G", null, null, false, 30.75, 0))
        l.add(
            Ingredient(
                0,
                "GRÅSTEN ITALIENSK SALAT 300 G",
                300.0,
                "G",
                null,
                null,
                false,
                20.95,
                0
            )
        )
        l.add(Ingredient(0, "GRÅSTEN KARRYSALAT 300 G", 300.0, "G", null, null, false, 20.95, 0))
        l.add(Ingredient(0, "GRÅSTEN MAKREL SALAT 300 G", 300.0, "G", null, null, false, 30.75, 0))
        l.add(
            Ingredient(
                0,
                "FINTHAKKET SKINKESALAT SF 150 G",
                150.0,
                "G",
                null,
                null,
                false,
                20.95,
                0
            )
        )
        l.add(Ingredient(0, "K-SALAT PEBERRODSSALAT 100 G", 100.0, "G", null, null, false, 20.0, 0))
        l.add(
            Ingredient(
                0,
                "GRAASTEN ITALIENSKSALAT 150 G",
                150.0,
                "G",
                null,
                null,
                false,
                12.95,
                0
            )
        )
        l.add(
            Ingredient(
                0,
                "KØDBYENS KARTOFFELSALAT 400 G",
                400.0,
                "G",
                null,
                null,
                false,
                25.95,
                0
            )
        )
        return l
    }
    
     */
}
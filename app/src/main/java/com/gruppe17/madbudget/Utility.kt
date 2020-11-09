package com.gruppe17.madbudget

import android.util.Log
import com.beust.klaxon.Klaxon
import com.gruppe17.madbudget.models.Ingredient
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

        fun getTestIngredientList(): List<Ingredient>{
            val l = ArrayList<Ingredient>()
            l.add(Ingredient(0, "FLÆSKESTEG 1,2-2,4 KG MED SVÆR", 1799.9999999999998, "G", null, null, false, 49.9, 0))
            l.add(Ingredient(0, "MÖRT GRYDESTEG 800-1600G", 1200.0, "G", null, null, false, 79.9, 0))
            l.add(Ingredient(0, "HAKKET OKSEKØD 8-12% 450G", 450.0, "G", null, null, false, 30.0, 0))
            l.add(Ingredient(0, "MÖRT TYKSTEGSBØFFER 300 G, UNGKVÆG", 300.0, "G", null, null, false, 55.0, 0))
            l.add(Ingredient(0, "HAKKET GRISE-& KALVEKØD 8-12% 500G", 500.0, "G", null, null, false, 38.5, 0))
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
            l.add(Ingredient(0, "COOP BRYSTFILET 13 %  800 GRAM ", 800.0, "GRAM", null, null, false, 49.95, 0))
            l.add(Ingredient(0, "SALAMI LAV FEDT 110 G - 3 STJERNET", 110.0, "G", null, null, false, 22.95, 0))
            l.add(Ingredient(0, "JÆGERPØLSE 9% FEDT 110 G. - 3 STJERNET", 110.0, "G", null, null, false, 16.95, 0))
            l.add(Ingredient(0, "COOP SØNDERJYSK SPEGEPØLSE 300 G", 300.0, "G", null, null, false, 30.95, 0))
            l.add(Ingredient(0, "COOP SPEGEPØLSE M/HVIDLØG 300 G", 300.0, "G", null, null, false, 30.95, 0))
            l.add(Ingredient(0, "COOP KYLLINGSTYKKER STEGT 150 G", 150.0, "G", null, null, false, 18.75, 0))
            l.add(Ingredient(0, "X-TRA SALAMI 200 G", 200.0, "G", null, null, false, 11.5, 0))
            l.add(Ingredient(0, "COOP KRYDERET KYLLINGE FILET 150 G", 150.0, "G", null, null, false, 20.95, 0))
            l.add(Ingredient(0, "COOP SPEGEPØLSE M/PEBER 300 G", 300.0, "G", null, null, false, 30.95, 0))
            l.add(Ingredient(0, "TYNDTSKÅRET RØGET SKINKE 100 G.", 100.0, "G", null, null, false, 18.95, 0))
            l.add(Ingredient(0, "TYNDTSKÅRET STEGT KYLLING 80 G.", 80.0, "G", null, null, false, 18.95, 0))
            l.add(Ingredient(0, "STRYHNS FRANSK POSTEJ 200 G", 200.0, "G", null, null, false, 18.5, 0))
            l.add(Ingredient(0, "STRYHNS GROVHAKKET LEVERPOSTEJ 200 G", 200.0, "G", null, null, false, 18.5, 0))
            l.add(Ingredient(0, "STRYHNS BACON-PEBER POSTEJ 300 G", 300.0, "G", null, null, false, 26.95, 0))
            l.add(Ingredient(0, "STRYHNS GROVHAKKET LEVERPOSTEJ 450 G", 450.0, "G", null, null, false, 31.5, 0))
            l.add(Ingredient(0, "STRYHNS RØGET MEDISTER 345 G", 345.0, "G", null, null, false, 27.5, 0))
            l.add(Ingredient(0, "GRILL-PØLSER 350 G. - LANGELÆNDER", 350.0, "G", null, null, false, 36.95, 0))
            l.add(Ingredient(0, "WIENER-PØLSER 330 G. - LANGELÆNDER", 330.0, "G", null, null, false, 36.95, 0))
            l.add(Ingredient(0, "JULEPOSTEJ MED BACON 450 G.", 450.0, "G", null, null, false, 30.5, 0))
            l.add(Ingredient(0, "SYLTE 400 G. - STRYHNS", 400.0, "G", null, null, false, 34.95, 0))
            l.add(Ingredient(0, "KØDPØLSE, SKIVER 125 G. -3-STJERNET", 125.0, "G", null, null, false, 16.95, 0))
            l.add(Ingredient(0, "SØNDERJYSK SPEGEPØLSE 125 G. - 3-STJERNET", 125.0, "G", null, null, false, 22.95, 0))
            l.add(Ingredient(0, "KARTOFFELSPEGEPØLSE 125 G. - 3-STJERNET", 125.0, "G", null, null, false, 22.95, 0))
            l.add(Ingredient(0, "GOURMET VINTERPØLSE 400 G M/SPINAT OG PEBER", 400.0, "G", null, null, false, 45.95, 0))
            l.add(Ingredient(0, "3-STJERNET SALAMI ORIGINAL 175 G", 175.0, "G", null, null, false, 21.95, 0))
            l.add(Ingredient(0, "ØKOLOGISK MILD SPEGEPØLSE 200 G.", 200.0, "G", null, null, false, 42.95, 0))
            l.add(Ingredient(0, "HAMBURGERRYG 90 G. - PÅLÆKKER", 90.0, "G", null, null, false, 16.95, 0))
            l.add(Ingredient(0, "TULIP BACON I SKIVER 3 X 125 G", 375.0, "G", 3, null, false, 39.95, 0))
            l.add(Ingredient(0, "BRUNCH PØLSER 300 G, STEFF HOULBERG", 300.0, "G", null, null, false, 32.95, 0))
            l.add(Ingredient(0, "RULLEPØLSE 80 G. - PÅLÆKKER", 80.0, "G", null, null, false, 16.95, 0))
            l.add(Ingredient(0, "GØL WIENERPØLSER 440 G", 440.0, "G", null, null, false, 40.95, 0))
            l.add(Ingredient(0, "GØL HOT DOG PØLSER 440 G", 440.0, "G", null, null, false, 40.95, 0))
            l.add(Ingredient(0, "GØL FRANKFURTER 500 G", 500.0, "G", null, null, false, 40.95, 0))
            l.add(Ingredient(0, "PÅLÆKKER BACON LEVERPOSTEJ 200 G", 200.0, "G", null, null, false, 16.95, 0))
            l.add(Ingredient(0, "PÅLÆKKER SØNDERJYSK SPEGEPØLSE 125 G", 125.0, "G", null, null, false, 17.95, 0))
            l.add(Ingredient(0, "GØL GRILLPØLSER 420 G", 420.0, "G", null, null, false, 40.95, 0))
            l.add(Ingredient(0, "KALKUNSALAMI 80 G", 80.0, "G", null, null, false, 16.95, 0))
            l.add(Ingredient(0, "KYLLINGE WIENERPØLSER 5 STK./250 G, HANEGAL, ØKO.", 250.0, "G", 5, null, false, 41.5, 0))
            l.add(Ingredient(0, "GÅRDLYKKE ROASTBEEF 70 G.", 70.0, "G", null, null, false, 20.95, 0))
            l.add(Ingredient(0, "GÅRDLYKKE RULLEPØLSE 90 G.", 90.0, "G", null, null, false, 20.95, 0))
            l.add(Ingredient(0, "GÅRDLYKKE KAMSTEG 90 G", 90.0, "G", null, null, false, 20.95, 0))
            l.add(Ingredient(0, "X-TRA KOGT SKINKE 150 G", 150.0, "G", null, null, false, 8.95, 0))
            l.add(Ingredient(0, "X-TRA BACON TERN 200 G", 200.0, "G", null, null, false, 13.25, 0))
            l.add(Ingredient(0, "X-TRA SKINKE STRIMLER 200 G", 200.0, "G", null, null, false, 14.0, 0))
            l.add(Ingredient(0, "X-TRA COCKTAIL PØLSER 200 G", 200.0, "G", null, null, false, 14.75, 0))
            l.add(Ingredient(0, "X-TRA GRILL PØLSER 550 G 10 STK", 550.0, "G", 10, null, false, 23.75, 0))
            l.add(Ingredient(0, "XTRA HOTDOG PØLSER 500 G 8 STK", 500.0, "G", 8, null, false, 26.25, 0))
            l.add(Ingredient(0, "X-TRA LEVERPOSTEJ 500 G", 500.0, "G", null, null, false, 9.95, 0))
            l.add(Ingredient(0, "X-TRA PARTY PØLSER 500 G 10 STK", 500.0, "G", 10, null, false, 22.25, 0))
            l.add(Ingredient(0, "X-TRA BACON 2 X 140 G", 280.0, "G", 2, null, false, 20.5, 0))
            l.add(Ingredient(0, "COOP PEPPERONI I SKIVER 150 G", 150.0, "G", null, null, false, 13.5, 0))
            l.add(Ingredient(0, "COOP RULLEPØLSE 100 G", 100.0, "G", null, null, false, 18.95, 0))
            l.add(Ingredient(0, "COOP HAMBURGERRYG 100 G", 100.0, "G", null, null, false, 17.5, 0))
            l.add(Ingredient(0, "COOP KOGT SKINKE 100 G", 100.0, "G", null, null, false, 18.25, 0))
            l.add(Ingredient(0, "KYLLINGEBRYST STEGT 90 G - COOP", 90.0, "G", null, null, false, 15.95, 0))
            l.add(Ingredient(0, "COOP ROASTBEEF 70 G.", 70.0, "G", null, null, false, 16.25, 0))
            l.add(Ingredient(0, "COOP BACON LEVERPOSTEJ 350 G", 350.0, "G", null, null, false, 13.95, 0))
            l.add(Ingredient(0, "SANDWICH SKIVER NATUREL 125 G. - DIT VALG", 125.0, "G", null, null, false, 16.95, 0))
            l.add(Ingredient(0, "XTRA HAMBURGERRYG 140 G", 140.0, "G", null, null, false, 12.5, 0))
            l.add(Ingredient(0, "X-TRA VARMRØGET LAKS MED PEBER 125 G", 125.0, "G", null, null, false, 30.95, 0))
            l.add(Ingredient(0, "TENAX FISKEFRIKADELLER 260 G 4 STK MSC", 260.0, "G", 4, null, false, 24.95, 0))
            l.add(Ingredient(0, "TENAX LAKSEFRIKADELLER 260 G 4 STK", 260.0, "G", 4, null, false, 24.95, 0))
            l.add(Ingredient(0, "LAUNIS MINI FISKEFRIKADELLER 180 G", 180.0, "G", null, null, false, 21.95, 0))
            l.add(Ingredient(0, "EKSTRA STORE REJER CAPELLA 400/200 G", 200.0, "G", null, null, false, 55.95, 0))
            l.add(Ingredient(0, "RØRÆG 150 G, ROYAL DANISH SEAFOOD", 150.0, "G", null, null, false, 13.95, 0))
            l.add(Ingredient(0, "VARMRØGET ØRREDFILET 125G", 125.0, "G", null, null, false, 20.75, 0))
            l.add(Ingredient(0, "VARMRØGET MAKREL PEBER 150 G", 150.0, "G", null, null, false, 21.95, 0))
            l.add(Ingredient(0, "HEL RØGET MAKREL 250 G", 250.0, "G", null, null, false, 22.95, 0))
            l.add(Ingredient(0, "HAVET'S FISKEFRIKADELLE 380 G", 380.0, "G", null, null, false, 32.5, 0))
            l.add(Ingredient(0, "X-TRA REJER I LAGE 175/100 G", 100.0, "G", null, null, false, 14.95, 0))
            l.add(Ingredient(0, "COOP XL REJER I LAGE 360/170 G MSC", 170.0, "G", null, null, false, 42.95, 0))
            l.add(Ingredient(0, "COOP RØGET LAKS 160 G", 160.0, "G", null, null, false, 56.95, 0))
            l.add(Ingredient(0, "COOP RØGET LAKS I SKIVER 100 G", 100.0, "G", null, null, false, 31.95, 0))
            l.add(Ingredient(0, "COOP GRAVAD LAKS  I SKIVER 100 G MSC", 100.0, "G", null, null, false, 31.95, 0))
            l.add(Ingredient(0, "K-S LÆKKERIER SKAGENSSALAT 250 G", 250.0, "G", null, null, false, 20.95, 0))
            l.add(Ingredient(0, "K-S MAKRELSALAT 150 G K-SALAT", 150.0, "G", null, null, false, 24.0, 0))
            l.add(Ingredient(0, "DILDDRESSING 300 ML BAHNCKE", 300.0, "ML", null, null, false, 18.25, 0))
            l.add(Ingredient(0, "GRÅSTEN HØNSE SALAT 300 G", 300.0, "G", null, null, false, 30.75, 0))
            l.add(Ingredient(0, "GRÅSTEN ITALIENSK SALAT 300 G", 300.0, "G", null, null, false, 20.95, 0))
            l.add(Ingredient(0, "GRÅSTEN KARRYSALAT 300 G", 300.0, "G", null, null, false, 20.95, 0))
            l.add(Ingredient(0, "GRÅSTEN MAKREL SALAT 300 G", 300.0, "G", null, null, false, 30.75, 0))
            l.add(Ingredient(0, "FINTHAKKET SKINKESALAT SF 150 G", 150.0, "G", null, null, false, 20.95, 0))
            l.add(Ingredient(0, "K-SALAT PEBERRODSSALAT 100 G", 100.0, "G", null, null, false, 20.0, 0))
            l.add(Ingredient(0, "GRAASTEN ITALIENSKSALAT 150 G", 150.0, "G", null, null, false, 12.95, 0))
            l.add(Ingredient(0, "KØDBYENS KARTOFFELSALAT 400 G", 400.0, "G", null, null, false, 25.95, 0))
            return l
        }
    }
}
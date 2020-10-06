package com.example.madbudget.salling

import android.util.Base64.URL_SAFE
import android.util.Base64.encode
import android.util.Log
import android.util.Log.INFO
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.*
import java.util.logging.Level.INFO

class TokenBuilder {
    companion object {
        fun getJWT(sub: String): String {

            var iss = "9e9fef2d-3a73-407b-bbf7-01995590e420"
            var secret = "aaf47bd2-7723-4632-8730-500970a8487e"

            /*
        var exp = System.currentTimeMillis()
        var mth = "GET"
        //var sub = "/v1/food-waste/?zip=8000"


        var header = "{ 'alg': 'HS256', 'typ': 'JWT' }"
        var payload = "{ 'exp': $exp, 'iss': '$iss', 'mth': '$mth', 'sub': '$sub' }"

        var encodedHeader = Base64.getUrlEncoder().encodeToString(header.toByteArray())
        var encodedPayload = Base64.getUrlEncoder().encodeToString(payload.toByteArray())

        var unencodedToken = "$encodedHeader.$encodedPayload"

        var hmacKey = Keys.hmacShaKeyFor(secret.toByteArray())
        */

            var jws = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setExpiration(Calendar.getInstance().time)
                .setIssuer(iss)
                .claim("mth", "GET")
                .setSubject(sub)
                .signWith(SignatureAlgorithm.HS256, secret.toByteArray())
                .compact()

            Log.i("Encoded Token", jws)

            return jws
        }
    }
}


// Header:  { 'alg': 'HS256', 'typ': 'JWT' }
// Payload: { 'exp': exp, 'iss': iss, 'mth': mth, 'sub': sub }
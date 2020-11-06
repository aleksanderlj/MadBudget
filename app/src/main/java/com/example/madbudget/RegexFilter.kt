package com.example.madbudget

class RegexFilter {
    val amountRegex = Regex("([0-9]+(([,.]?)[0-9]*(-)))?([0-9]+(([,.]?)[0-9]*))( ?)(KG|GRAM|G|CL|ML|LITER|L)")
    val stkXRegex = Regex("([0-9]+)( ?)([X]|STK)")
    val caMaxMinRegex = Regex("\b(MIN|CA|MAX)[ .]+([0-9]+)( ?)(KG|GRAM|G|CL|ML|LITER|L)")
    val halfLiterRegex = Regex("\b(1/2)( ?)(LITER|L)")
}
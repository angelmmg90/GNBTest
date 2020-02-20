package com.macdonald.angel.data.model

data class TransactionDetailsModel (
    val product : String = "",
    val amount : Double,
    val currency : String = "",
    val conversionToEUR: Double
)
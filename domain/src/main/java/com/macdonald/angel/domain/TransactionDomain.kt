package com.macdonald.angel.domain

data class TransactionDomain (
    val sku : String = "",
    val amount : Double,
    val currency : String = ""
)
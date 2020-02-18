package com.macdonald.angel.data.model

data class ProductModel (
    val name : String,
    val transactions : List<TransactionModel>?,
    val totalSum : Double
)
package com.macdonald.angel.data.model

data class ProductModel (
    val name : String,
    val transactions : List<TransactionModel>? = emptyList(),
    val totalSum : Double = 0.0
)
package com.macdonald.angel.data.model

class ProductDetailsModel(
    val name: String,
    val transactions: List<TransactionDetailsModel>? = emptyList(),
    val totalSum: Double = 0.0
)
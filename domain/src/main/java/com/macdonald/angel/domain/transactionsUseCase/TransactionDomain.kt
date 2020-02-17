package com.macdonald.angel.domain.transactionsUseCase

data class TransactionDomain (
    val sku : String = "",
    val amount : Double,
    val currency : String = ""
)
package com.macdonald.angel.gnb.data

import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.domain.transactionsUseCase.TransactionDomain

fun TransactionDomain.toTransactionModel(): TransactionModel =
    TransactionModel(
        sku,
        amount,
        currency
    )
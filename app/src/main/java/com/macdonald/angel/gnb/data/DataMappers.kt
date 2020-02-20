package com.macdonald.angel.gnb.data

import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.model.RateModel
import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.domain.ratesUseCase.RateDomain
import com.macdonald.angel.domain.transactionsUseCase.TransactionDomain
import com.macdonald.angel.gnb.data.database.entities.ProductEntity
import com.macdonald.angel.gnb.data.database.entities.RateEntity
import com.macdonald.angel.gnb.data.database.entities.TransactionEntity

fun TransactionDomain.toTransactionModel(): TransactionModel =
    TransactionModel(
        sku,
        amount,
        currency
    )

fun TransactionModel.toTransactionEntity(): TransactionEntity =
    TransactionEntity(
        product = product,
        amount = amount,
        currency = currency
    )

fun TransactionEntity.toTransactionModel(): TransactionModel =
    TransactionModel(
        product = product,
        amount = amount,
        currency = currency
    )

fun RateDomain.toRateModel(): RateModel =
    RateModel(
        from,
        to,
        rate
    )

fun RateEntity.toRateModel(): RateModel =
    RateModel(
        from,
        to,
        rate
    )

fun RateModel.toRateEntity(): RateEntity =
    RateEntity(
        from = from,
        to = to,
        rate = rate
    )

fun ProductModel.toProductEntity(): ProductEntity {
    val transactionListEntity = ArrayList<TransactionEntity>()
    transactions?.forEach { transactionListEntity.add(it.toTransactionEntity()) }
    return ProductEntity(
        name = name,
        transactions = transactionListEntity,
        totalSum = totalSum
    )
}

fun ProductEntity.toProductModel(): ProductModel {
    val transactionListModel = ArrayList<TransactionModel>()
    transactions?.forEach { transactionListModel.add(it.toTransactionModel()) }
    return ProductModel(
        name = name,
        transactions = transactionListModel,
        totalSum = totalSum
    )
}

fun ArrayList<ProductModel>.getProductsFromTransactionsList(transactionList: ArrayList<TransactionModel>):
        ArrayList<ProductModel> {

        transactionList.forEach {transaction->
            lateinit var newProduct: ProductModel
            if(this.isEmpty()) {
                newProduct = ProductModel(
                    name = transaction.product
                )
                this.add(newProduct)
            }else{
                newProduct = ProductModel(
                    name = transaction.product
                )
                var productNotAdded = !this.contains(newProduct)
                if(productNotAdded){
                    this.add(newProduct)
                }
            }

        }

    return this
}
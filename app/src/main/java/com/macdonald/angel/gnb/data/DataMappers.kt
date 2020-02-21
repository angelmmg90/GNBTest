package com.macdonald.angel.gnb.data

import com.macdonald.angel.data.model.*
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

fun TransactionDetailsModel.toTransactionEntity(): TransactionEntity =
    TransactionEntity(
        product = product,
        amount = amount,
        currency = currency,
        conversionToChosenCurrency = conversionToChosenCurrency
    )

fun TransactionEntity.toTransactionModel(): TransactionModel =
    TransactionModel(
        product = product,
        amount = amount,
        currency = currency
    )

fun TransactionEntity.toTransactionDetailModel(): TransactionDetailsModel =
    TransactionDetailsModel(
        product = product,
        amount = amount,
        currency = currency,
        conversionToChosenCurrency = conversionToChosenCurrency
    )

fun TransactionModel.toTransactionDetailModel(): TransactionDetailsModel =
    TransactionDetailsModel(
        product = product,
        amount = amount,
        currency = currency
    )

fun TransactionDetailsModel.toTransactionModel(): TransactionModel =
    TransactionModel(
        product = product,
        amount = amount,
        currency = currency
    )

fun List<TransactionModel>.toTransactionDetailsModelList(): List<TransactionDetailsModel> {
    val transactionDetailsList = ArrayList<TransactionDetailsModel>()

    this.forEach {
        transactionDetailsList.add(it.toTransactionDetailModel())
    }

    return transactionDetailsList
}

fun List<TransactionDetailsModel>.toTransactionModelList(): List<TransactionModel> {
    val transactionModelList = ArrayList<TransactionModel>()

    this.forEach {
        transactionModelList.add(it.toTransactionModel())
    }

    return transactionModelList
}

fun RateDomain.toRateModel(): RateModel =
    RateModel(
        from = from,
        to = to,
        rate = rate
    )

fun RateDomain.toRateModel(rateChosenCurrency: Double): RateModel =
    RateModel(
        from = from,
        to = to,
        rate = rate,
        rateChosenCurrency = rateChosenCurrency
    )

fun RateEntity.toRateModel(): RateModel =
    RateModel(
        from,
        to,
        rate,
        rateChosenCurrency
    )

fun RateModel.toRateEntity(): RateEntity =
    RateEntity(
        from = from,
        to = to,
        rate = rate,
        rateChosenCurrency = rateChosenCurrency
    )


fun ProductDetailsModel.toProductEntity(): ProductEntity {
    val transactionListEntity = ArrayList<TransactionEntity>()
    transactions?.forEach { transactionListEntity.add(it.toTransactionEntity()) }
    return ProductEntity(
        name = name,
        transactions = transactionListEntity,
        totalSum = totalSum
    )
}

fun List<ProductModel>.toProductDetailsList(): List<ProductDetailsModel> {
    val productDetailsList = ArrayList<ProductDetailsModel>()

    this.forEach {
        productDetailsList.add(it.toProductDetails())
    }

    return productDetailsList
}

fun List<ProductDetailsModel>.toProductModelList(): List<ProductModel> {
    val productModelList = ArrayList<ProductModel>()

    this.forEach {
        productModelList.add(it.toProductModel())
    }

    return productModelList
}


fun ProductModel.toProductDetails(): ProductDetailsModel =
    ProductDetailsModel(
        name
    )

fun ProductDetailsModel.toProductModel(): ProductModel =
    ProductModel(
        name
    )


fun ProductEntity.toProductDetailsModel(): ProductDetailsModel {
    val transactionDetailsListModel = ArrayList<TransactionDetailsModel>()
    transactions?.forEach { transactionDetailsListModel.add(it.toTransactionDetailModel()) }
    return ProductDetailsModel(
        name = name,
        transactions = transactionDetailsListModel,
        totalSum = totalSum
    )
}

fun ProductEntity.toProductModel(): ProductModel =
    ProductModel(
        name = name
    )


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
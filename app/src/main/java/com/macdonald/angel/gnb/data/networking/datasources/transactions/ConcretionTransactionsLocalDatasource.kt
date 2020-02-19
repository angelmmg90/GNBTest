package com.macdonald.angel.gnb.data.networking.datasources.transactions

import android.content.Context
import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.data.sources.TransactionsLocalDatasource
import com.macdonald.angel.data.sources.TransactionsRemoteDatasource
import com.macdonald.angel.domain.transactionsUseCase.TransactionDomain
import com.macdonald.angel.gnb.data.database.GNBLocalDatabase
import com.macdonald.angel.gnb.data.database.entities.TransactionEntity
import com.macdonald.angel.gnb.data.networking.NetworkObject
import com.macdonald.angel.gnb.data.toProductModel
import com.macdonald.angel.gnb.data.toTransactionEntity
import com.macdonald.angel.gnb.data.toTransactionModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.HttpException
import kotlin.coroutines.resume

class ConcretionTransactionsLocalDatasource(private var db: GNBLocalDatabase) : TransactionsLocalDatasource {

    override suspend fun persistTransactionsIntoDatabase(transactions: List<TransactionModel>): Boolean {
        val transactionsEntityList = ArrayList<TransactionEntity>()

        return try {
            transactions.forEach {
                transactionsEntityList.add(it.toTransactionEntity())
            }

            db.transactionDAO().inserTransactions(transactionsEntityList)
            return true
        } catch (e: java.lang.Exception) {
            false
        }
    }

    override suspend fun getTransactionsFromLocal(): List<TransactionModel> {
        val transactionListModel = ArrayList<TransactionModel>()

        db.transactionDAO().getTransactions().forEach {
            transactionListModel.add(it.toTransactionModel())
        }

        return transactionListModel
    }

}


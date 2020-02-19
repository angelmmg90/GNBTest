package com.macdonald.angel.gnb.data.networking.datasources.transactions

import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.data.sources.TransactionsLocalDatasource
import com.macdonald.angel.gnb.data.database.GNBLocalDatabase
import com.macdonald.angel.gnb.data.database.entities.TransactionEntity
import com.macdonald.angel.gnb.data.toTransactionEntity
import com.macdonald.angel.gnb.data.toTransactionModel

class ConcretionTransactionsLocalDatasource(private var db: GNBLocalDatabase) : TransactionsLocalDatasource {

    override suspend fun persistTransactionsIntoDatabase(transactions: List<TransactionModel>): Boolean {
        val transactionsEntityList = ArrayList<TransactionEntity>()

        return try {
            transactions.forEach {
                transactionsEntityList.add(it.toTransactionEntity())
            }

            db.transactionDAO().insertIncomingTransactions(transactionsEntityList)
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


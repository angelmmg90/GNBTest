package com.macdonald.angel.gnb.data.database.daos

import androidx.room.*
import com.macdonald.angel.gnb.data.database.entities.ProductEntity

@Dao
interface ProductDAO {

    @Transaction
    suspend fun insertIncomingProducts(incomingProducts: List<ProductEntity>){
        deleteAllProducts()
        insertProducts(incomingProducts)
    }
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProduct(rate: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Delete
    fun deleteProduct(rate: ProductEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateProduct(product: ProductEntity)

    @Query("SELECT * from product_entity")
    fun getProucts(): List<ProductEntity>

    @Query("DELETE FROM product_entity")
    fun deleteAllProducts()

}
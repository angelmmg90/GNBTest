package com.macdonald.angel.gnb.data.networking.datasources.products

import com.macdonald.angel.data.model.ProductDetailsModel
import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.sources.ProductsLocalDatasource
import com.macdonald.angel.gnb.data.database.GNBLocalDatabase
import com.macdonald.angel.gnb.data.database.entities.ProductEntity
import com.macdonald.angel.gnb.data.toProductDetailsModel
import com.macdonald.angel.gnb.data.toProductEntity
import com.macdonald.angel.gnb.data.toProductModel

class ConcretionProductLocalDatasource(private var db: GNBLocalDatabase) : ProductsLocalDatasource {


    override suspend fun persistProductsIntoDatabase(products: List<ProductDetailsModel>): Boolean {
        val productsEntityList = ArrayList<ProductEntity>()

        return try {
            products.forEach {
                productsEntityList.add(it.toProductEntity())
            }

            db.productDAO().insertIncomingProducts(productsEntityList)
            return true
        } catch (e: java.lang.Exception) {
            false
        }
    }

    override suspend fun getProductDetailsFromLocal(): List<ProductDetailsModel> {
        val listProductDetailsModel = ArrayList<ProductDetailsModel>()

        db.productDAO().getProucts().forEach {
            listProductDetailsModel.add(it.toProductDetailsModel())
        }

        return listProductDetailsModel
    }

    override suspend fun getProductModelFromLocal(): List<ProductModel> {
        val listProductModel = ArrayList<ProductModel>()

        db.productDAO().getProucts().forEach {
            listProductModel.add(it.toProductModel())
        }

        return listProductModel
    }

    override suspend fun updateProductDetails(productDetails: ProductDetailsModel): Boolean {
        return try {
            db.productDAO().updateProduct(productDetails.toProductEntity())
            return true
        } catch (e: java.lang.Exception) {
            false
        }
    }

}


package com.example.sportsstore.data.repo

import com.example.sportsstore.data.Product
import com.example.sportsstore.data.repo.source.ProductDataSource
import com.example.sportsstore.data.repo.source.ProductLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    val remoteDataSource: ProductDataSource,
    val productLocalDataSource: ProductLocalDataSource
) : ProductRepository {
    override fun getProducts(): Single<List<Product>> = remoteDataSource.getProducts()

    override fun getFavoriteProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorites(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorites(): Completable {
        TODO("Not yet implemented")
    }
}
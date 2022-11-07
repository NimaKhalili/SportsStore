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
    override fun getProducts(sort: Int): Single<List<Product>> = productLocalDataSource.getFavoriteProducts()
        .flatMap { favoriteProducts ->
            remoteDataSource.getProducts(sort).doOnSuccess {
                val favoriteProductIds = favoriteProducts.map{
                    it.id
                }
                it.forEach{
                    product ->
                    if(favoriteProductIds.contains(product.id))
                        product.isFavorite = true
                }
            }
        }

    override fun getFavoriteProducts(): Single<List<Product>> = productLocalDataSource.getFavoriteProducts()

    override fun addToFavorites(product: Product): Completable = productLocalDataSource.addToFavorites(product)

    override fun deleteFromFavorites(product: Product): Completable = productLocalDataSource.deleteFromFavorites(product)
}
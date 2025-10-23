package com.example.ejercicio1.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("products/")
    suspend fun getProducts(): List<Product>

    @GET("products/{id}/")
    suspend fun getProduct(@Path("id") id: Int): Product

    @POST("products/")
    suspend fun createProduct(@Body product: Product): Product

    @PUT("products/{id}/")
    suspend fun updateProduct(@Path("id") id: Int, @Body product: Product): Product

    @DELETE("products/{id}/")
    suspend fun deleteProduct(@Path("id") id: Int)

    companion object {
        private const val BASE_URL = "http://192.168.0.156:8000/api/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
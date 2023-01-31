package com.omersungur.e_ticaretandroid.service

import com.omersungur.e_ticaretandroid.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ProductAPI {

    //https://raw.githubusercontent.com/atilsamancioglu/BTK23-DataSet/main/products.json

    @GET("atilsamancioglu/BTK23-DataSet/main/products.json")
    suspend fun getProducts() : Response<List<Product>>
}
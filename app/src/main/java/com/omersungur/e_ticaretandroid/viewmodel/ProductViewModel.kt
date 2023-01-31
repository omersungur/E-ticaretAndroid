package com.omersungur.e_ticaretandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omersungur.e_ticaretandroid.model.Product
import com.omersungur.e_ticaretandroid.service.ProductAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductViewModel : ViewModel() {

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var job : Job? = null
    val productList = MutableLiveData<List<Product>>()
    val basket = MutableLiveData<List<Product>>()
    val totalBasket = MutableLiveData<Int>()

    fun downloadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ProductAPI::class.java)

        job = viewModelScope.launch(context = Dispatchers.IO) {
            val response = retrofit.getProducts()
            withContext(Dispatchers.Main) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        productList.value = it
                    }
                }
            }
        }
    }

    fun addToBasket(product : Product) {

        if(basket.value != null) { // basket.value içi boş değilse eleman var demektir.
            val arrayList = ArrayList(basket.value) // içinde eleman varsa ilk olarak bu elemanları bir liste içine alıyoruz.
            if(arrayList.contains(product)) { // Bir ürünü birden fazla ekleyince adet değişkenini arttırmak için o ürün var mı diye kontrol ediyoruz.
                val indexOfFirst = arrayList.indexOfFirst { it == product } // o elemanın bulunduğu ilk indis artık onun tek konumu olacak(Birden fazla rowda olmayacak)
                val relatedProduct = arrayList.get(indexOfFirst) // eklenmeye çalışan elemanın indisini alarak o ürün neyse artık onu biliyorum.
                relatedProduct.count++ // Bu eklemeye çalıştığımız ürün listede olduğu için sayısını 1 arttırırız
                basket.value = arrayList
            }
            else {
                product.count++ // elemanı eklediğimiz gibi adeti de artmalı o yüzden burada da arttırdık.
                arrayList.add(product) // yeni eklenecek product'ı bu listeye dahil ediyoruz(öncekilerin yanına).
                basket.value = arrayList // basket.value içine de güncel olan listemizi ekliyoruz. Bir ürün eklenirse sorunsuz bir şekilde listemizde olacak.
            }
        }
        else {
            val arrayList = arrayListOf(product)
            product.count++ // elemanı eklediğimiz gibi adeti de artmalı o yüzden burada da arttırdık.
            basket.value = arrayList
        }

        basket.value.let {
            refreshTotalValue(it!!)
        }
    }

    fun refreshTotalValue (listOfProduct: List<Product>) {
        var total = 0
        listOfProduct.forEach { product ->
            var price = product.price.toIntOrNull()
            price?.let {
                val count = product.count
                total += it * count
            }
        }

        totalBasket.value = total
    }

    fun deleteProductFromBasket(product: Product) {
        basket.value?.let {
            var arrayList = ArrayList(basket.value)
            arrayList.remove(product)
            basket.value = arrayList
            refreshTotalValue(arrayList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
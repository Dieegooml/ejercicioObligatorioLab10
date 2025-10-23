package com.example.ejercicio1.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio1.model.ApiService
import com.example.ejercicio1.model.Product
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val api = ApiService.create()

    var products by mutableStateOf<List<Product>>(emptyList())
    var isLoading by mutableStateOf(true)
    var errorMessage by mutableStateOf<String?>(null)

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            isLoading = true
            try {
                products = api.getProducts()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun createProduct(product: Product) {
        viewModelScope.launch {
            try {
                val newProduct = api.createProduct(product)
                products = products + newProduct
            } catch (e: Exception) {
                errorMessage = e.message
            }
        }
    }

    // Ahora la firma acepta un Product entero (coherente con las llamadas desde navigation y form)
    fun updateProduct(updatedProduct: Product) {
        viewModelScope.launch {
            try {
                val product = api.updateProduct(updatedProduct.id, updatedProduct)
                products = products.map { if (it.id == updatedProduct.id) product else it }
            } catch (e: Exception) {
                errorMessage = e.message
            }
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            try {
                api.deleteProduct(id)
                products = products.filterNot { it.id == id }
            } catch (e: Exception) {
                errorMessage = e.message
            }
        }
    }
}

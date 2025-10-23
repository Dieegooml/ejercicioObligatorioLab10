package com.example.ejercicio1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ejercicio1.view.ProductDetailScreen
import com.example.ejercicio1.view.ProductFormScreen
import com.example.ejercicio1.view.ProductListScreen
import com.example.ejercicio1.viewmodel.ProductViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: ProductViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            ProductListScreen(
                viewModel = viewModel,
                navController = navController,
                onItemClick = { product ->
                    navController.navigate("detail/${product.id}")
                }
            )
        }

        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            val product = viewModel.products.find { it.id == id }
            product?.let {
                ProductDetailScreen(
                    product = it,
                    viewModel = viewModel,
                    navController = navController,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        // Ruta para añadir nuevo producto
        composable("add") {
            ProductFormScreen(
                onSave = { newProduct ->
                    viewModel.createProduct(newProduct)
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }

        // Ruta para editar producto existente
        composable("edit/{id}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            val product = viewModel.products.find { it.id == productId }
            product?.let {
                ProductFormScreen(
                    product = it,
                    onSave = { updatedProduct ->
                        viewModel.updateProduct(updatedProduct)
                        navController.popBackStack()
                    },
                    onCancel = { navController.popBackStack() }
                )
            }
        }
    }
}

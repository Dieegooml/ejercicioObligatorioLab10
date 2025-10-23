package com.example.ejercicio1.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ejercicio1.model.Product

@Composable
fun ProductFormScreen(
    product: Product? = null, // Si viene un producto → modo edición
    onSave: (Product) -> Unit,
    onCancel: () -> Unit
) {
    // Estados para los campos del formulario
    var title by remember { mutableStateOf(product?.title ?: "") }
    var description by remember { mutableStateOf(product?.description ?: "") }
    var price by remember { mutableStateOf(product?.price?.toString() ?: "") }
    var thumbnail by remember { mutableStateOf(product?.thumbnail ?: "") }

    // Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (product == null) "Agregar Producto" else "Editar Producto",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Campo título
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo descripción
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo precio
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Precio (S/.)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo imagen
        OutlinedTextField(
            value = thumbnail,
            onValueChange = { thumbnail = it },
            label = { Text("URL de Imagen") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                val parsedPrice = price.toDoubleOrNull() ?: 0.0
                val newProduct = Product(
                    id = product?.id ?: (0..1000000).random(), // genera id aleatorio si es nuevo
                    title = title,
                    description = description,
                    price = parsedPrice,
                    thumbnail = thumbnail
                )
                onSave(newProduct)
            }) {
                Text("Guardar")
            }

            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Cancelar")
            }
        }
    }
}

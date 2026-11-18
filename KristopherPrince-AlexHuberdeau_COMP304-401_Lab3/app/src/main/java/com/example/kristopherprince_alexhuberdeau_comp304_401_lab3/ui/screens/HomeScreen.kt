package com.example.kristopherprince_alexhuberdeau_comp304_401_lab3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kristopherprince_alexhuberdeau_comp304_401_lab3.data.Product
import com.example.kristopherprince_alexhuberdeau_comp304_401_lab3.ui.viewmodel.ProductViewModel
// added for excercise 1
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

// Main screen displaying all products
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel(),
    windowSizeClass: WindowSizeClass = calculateWindowSizeClass(LocalActivity.current)
) {
    val products by viewModel.allProducts.observeAsState(emptyList())
    println("HomeScreen products: $products") // Log to verify data
    val isExpandedScreen = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    //added for excerise 1
    var searchId by remember { mutableStateOf("")}
    val productFilter = if (searchId.isBlank()) products
    else products.filter { it.id.toString().contains(searchId.trim()) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product List") },
                actions = {
                    IconButton(onClick = { navController.navigate("Favorites") }) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favorites")
                    }
                    IconButton(onClick = { navController.navigate("Add") }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Product")
                    }
                }
            )
        }
    ) { paddingValues ->
        // added for excerise 1
        Column(modifier = Modifier.padding(paddingValues))
        {
            OutlinedTextField(
                value = searchId, onValueChange = { searchId = it },
                label = { Text("Search product by id") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp)
            )

            if (isExpandedScreen) {
                Row(
                    modifier = Modifier.fillMaxWidth().weight(2f)
                ) {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(productFilter) { product ->
                            ProductItem(
                                product = product,
                                onEdit = {
                                    navController.navigate("edit/${product.id}")
                                },
                                onDelete = { viewModel.delete(product) },
                                onToggleFavorite = { viewModel.toggleFavorite(product) },
                                modifier = Modifier
                                    .clickable { selectedProduct = product }
                                    .background(
                                        if (product == selectedProduct)
                                            MaterialTheme.colorScheme.secondaryContainer
                                        else Color.Transparent
                                    )
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(16.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        selectedProduct?.let { product ->
                            Column {
                                Text(product.name, style = MaterialTheme.typography.headlineMedium)
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    "Price: $${product.price}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    "Category: ${product.category}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    "Delivery Date: ${product.deliveryDate}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } ?: Text(
                            "Select a product",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.weight(3f)) {
                    items(productFilter) { product ->
                        ProductItem(
                            product = product,
                            onEdit = { navController.navigate("edit/${product.id}") },
                            onDelete = { viewModel.delete(product) },
                            onToggleFavorite = { viewModel.toggleFavorite(product) },
                            modifier = Modifier
                                .background(
                                    if (product == selectedProduct)
                                        MaterialTheme.colorScheme.secondaryContainer
                                    else Color.Transparent
                                )
                        )
                    }
                }
            }
        }
    }
}
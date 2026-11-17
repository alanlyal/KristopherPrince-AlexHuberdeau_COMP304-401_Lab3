package com.example.kristopherprince_alexhuberdeau_comp304_401_lab3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.kristopherprince_alexhuberdeau_comp304_401_lab3.data.Product
//added for excerise 1
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
@Composable
fun ProductItem(
    product: Product,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    //added for excerise 1

    var confrim by remember { mutableStateOf(false) }
    if (confrim)
    {
        AlertDialog(onDismissRequest = {confrim = false},
            title = {Text("delete the product")},
            text = {Text("do you want to delete ${product.name}")},
            confirmButton = {TextButton(onClick = {confrim = false
            onDelete()})
            {
                Text("yes")
            }
            },
            dismissButton ={
                TextButton(onClick = {confrim = false})
                {
                    Text("no")
                }
                }
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Product info column without clickable modifier
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, style = MaterialTheme.typography.titleMedium)
                Text("$${product.price}", style = MaterialTheme.typography.bodyMedium)
                Text(product.category, style = MaterialTheme.typography.bodySmall)
            }

            // Action buttons
            Row {
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.testTag("favorite_button")
                ) {
                    Icon(
                        imageVector = if (product.isFavorite) Icons.Default.Favorite
                        else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (product.isFavorite) Color.Red
                        else MaterialTheme.colorScheme.onSurface
                    )
                }

                IconButton(
                    onClick = {
                        println("Edit clicked for product ${product.id}")
                        onEdit()
                    },
                    modifier = Modifier.testTag("edit_button")
                ) {
                    Icon(Icons.Default.Edit, "Edit")
                }
                    //updated for excercise 1
                IconButton(
                    onClick = {confrim = true},
                    modifier = Modifier.testTag("delete_button")
                ) {
                    Icon(Icons.Default.Delete, "Delete")
                }
            }
        }
    }
}
package com.example.kristopherprince_alexhuberdeau_comp304_401_lab3.data
import androidx.room.PrimaryKey
import androidx.room.Entity

// Represents a product entity in the Room database
@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Double,
    val deliveryDate: String,
    val category: String,
    val isFavorite: Boolean,
    // added for excerise 1
    val quantity: Int

)
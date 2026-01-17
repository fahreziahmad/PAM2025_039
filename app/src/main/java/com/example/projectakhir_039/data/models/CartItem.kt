package com.example.projectakhir_039.data.models

import com.google.gson.annotations.SerializedName

data class CartItem(
    @SerializedName("id") val id: Int,
    @SerializedName("nama_sepatu") val name: String, // Map dari database 'nama_sepatu'
    @SerializedName("harga") val price: Double,      // Map dari database 'harga'
    @SerializedName("jumlah") val quantity: Int,    // Map dari database 'jumlah'
    @SerializedName("ukuran") val ukuran: Int,      // Map dari database 'ukuran'
    @SerializedName("total_harga") val totalHarga: Double? = 0.0,

    // Field di bawah ini TIDAK ADA di tabel database Anda (berdasarkan screenshot),
    // jadi berikan nilai default agar tidak crash saat parsing.
    val productId: Int = 0,
    val imageResId: Int = 0,
    val userId: Int = 0
)
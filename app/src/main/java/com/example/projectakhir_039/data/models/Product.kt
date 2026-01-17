// Lokasi: app/src/main/java/com/example/projectakhir_039/data/models/Product.kt
package com.example.projectakhir_039.data.models

import java.text.NumberFormat
import java.util.Locale

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val stock: Int,
    val category: String,
    val rating: Double = 0.0,
    // Gunakan String? agar bisa membaca URL foto dari database
    val image_url: String? = null,
    // imageResId tetap ada untuk menangani data lama (dummy) agar tidak error
    val imageResId: Int = 0
)
// 2. TARUH DI SINI (Di luar kurung kurawal data class)
// Ini disebut "Top-level function" agar bisa dipanggil dari file mana pun
fun formatRupiah(number: Double): String {
    val localeID = Locale("in", "ID")
    val numberFormat = NumberFormat.getCurrencyInstance(localeID)
    // Menambahkan spasi setelah Rp dan menghilangkan angka desimal ,00
    return numberFormat.format(number).replace("Rp", "Rp ").replace(",00", "")
}
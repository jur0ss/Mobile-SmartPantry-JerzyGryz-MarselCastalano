package com.example.mobile_smart_pantry_project_iv

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ListAdapter
import com.example.mobile_smart_pantry_project_iv.databinding.ActivityMainBinding
import com.example.mobile_smart_pantry_project_iv.model.Product
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var listAdapter: ArrayAdapter<String>

    private val productList = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            productList
        )

        binding.listViewProducts.adapter = listAdapter

        loadProducts()
    }

    @Serializable
    data class ProductList(
        val products: List<Product>
    )


    private fun loadProducts() {
        try {
            val inputStream = resources.openRawResource(R.raw.pantry)
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val json = Json { ignoreUnknownKeys = true }
            val loadedList = json.decodeFromString<ProductList>(jsonString)

            productList.clear()
            productList.addAll(
                loadedList.products.map { "${it.name} (${it.category}) *${it.quantity}" }
            )

            listAdapter.notifyDataSetChanged()

        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Błąd odczytu JSON",
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }

}
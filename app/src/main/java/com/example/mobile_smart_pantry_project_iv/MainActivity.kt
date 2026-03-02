package com.example.mobile_smart_pantry_project_iv

import android.R.attr.data
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobile_smart_pantry_project_iv.databinding.ActivityMainBinding
import com.example.mobile_smart_pantry_project_iv.model.Product
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val productList = mutableListOf<Product>()

    private val editProductLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {

                val data = result.data

                val updatedProduct = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    data?.getSerializableExtra("UPDATED_PRODUCT", Product::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    data?.getSerializableExtra("UPDATED_PRODUCT") as? Product
                }

                val position = data?.getIntExtra("POSITION", -1) ?: -1

                if (updatedProduct != null && position >= 0) {
                    productList[position] = updatedProduct
                    (binding.listViewProducts.adapter as ProductAdapter).notifyDataSetChanged()
                }
            }
        }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
            productList.addAll(loadedList.products)

            binding.listViewProducts.adapter = ProductAdapter(this, productList)

            binding.listViewProducts.setOnItemClickListener { _, _, position, _ ->
                val intent = Intent(this, EditProductActivity::class.java)
                intent.putExtra("EXTRA_PRODUCT", productList[position])
                intent.putExtra("EXTRA_POSITION", position)
                editProductLauncher.launch(intent)
            }


        } catch (e: Exception) {
            Toast.makeText(this, "Błąd odczytu JSON", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}

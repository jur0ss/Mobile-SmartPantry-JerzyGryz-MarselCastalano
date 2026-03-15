package com.example.mobile_smart_pantry_project_iv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_smart_pantry_project_iv.databinding.ActivityEditProductBinding
import com.example.mobile_smart_pantry_project_iv.model.Product

class EditProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProductBinding
    private lateinit var product: Product
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pobieramy produkt przekazany w Intent
        val receivedProduct = intent.getSerializableExtra("EXTRA_PRODUCT") as? Product
        position = intent.getIntExtra("EXTRA_POSITION", -1)

        if (receivedProduct != null) {
            product = receivedProduct
            // Wypełniamy pola tekstowe
            binding.editName.setText(product.name)
            binding.editCategory.setText(product.category)
            binding.editQuantity.setText(product.quantity.toString())
        }

        binding.saveButton.setOnClickListener {
            val name = binding.editName.text.toString().trim()
            val category = binding.editCategory.text.toString().trim()
            val qtyString = binding.editQuantity.text.toString()

            if (name.isNotEmpty()) {
                // Aktualizujemy dane obiektu
                product.name = name
                product.category = category
                product.quantity = qtyString.toIntOrNull() ?: 1

                val resultIntent = Intent()
                resultIntent.putExtra("UPDATED_PRODUCT", product)
                resultIntent.putExtra("POSITION", position)

                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                binding.editName.error = "Podaj nazwę produktu!"
            }
        }
    }
}
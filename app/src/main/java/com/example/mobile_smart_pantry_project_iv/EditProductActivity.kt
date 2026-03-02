package com.example.mobile_smart_pantry_project_iv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        product = intent.getSerializableExtra("EXTRA_PRODUCT") as Product
        position = intent.getIntExtra("EXTRA_POSITION", -1)

        binding.editName.setText(product.name)
        binding.editCategory.setText(product.category)
        binding.editQuantity.setText(product.quantity.toString())

        binding.saveButton.setOnClickListener {
            product.name = binding.editName.text.toString()
            product.category = binding.editCategory.text.toString()
            product.quantity = binding.editQuantity.text.toString().toInt()

            val resultIntent = Intent()
            resultIntent.putExtra("UPDATED_PRODUCT", product)
            resultIntent.putExtra("POSITION", position)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}

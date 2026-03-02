package com.example.mobile_smart_pantry_project_iv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mobile_smart_pantry_project_iv.model.Product

class ProductAdapter(
    context: Context,
    private val products: List<Product>
) : ArrayAdapter<Product>(context, 0, products) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_product, parent, false)

        val product = products[position]

        val image = view.findViewById<ImageView>(R.id.imageProduct)
        val name = view.findViewById<TextView>(R.id.textName)
        val category = view.findViewById<TextView>(R.id.textCategory)
        val quantity = view.findViewById<TextView>(R.id.textQuantity)

        name.text = product.name
        category.text = product.category
        quantity.text = "Ilość: ${product.quantity}"

        val imageName = product.imageref.substringBefore(".")
        val resId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        if (resId != 0) image.setImageResource(resId)

        return view
    }
}

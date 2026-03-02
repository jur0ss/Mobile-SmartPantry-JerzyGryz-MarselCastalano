package com.example.mobile_smart_pantry_project_iv.model

import kotlinx.serialization.Serializable

@Serializable
data class Product (
    var uuid: String,
    var name: String,
    var quantity: Int,
    var category: String,
    var imageref: String


): java.io.Serializable

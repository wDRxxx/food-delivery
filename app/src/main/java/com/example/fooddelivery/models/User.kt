package com.example.fooddelivery.models

import java.io.Serializable

data class User(
    var name: String,
    var email: String,
    var phone: String,
    var bio: String
) : Serializable

data class Address(
    var type: String,
    var address: String
) : Serializable

data class Addresses(
    var items: List<Address>
) : Serializable
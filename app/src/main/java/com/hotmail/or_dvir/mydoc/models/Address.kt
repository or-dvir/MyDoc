package com.hotmail.or_dvir.mydoc.models

data class Address(
    val street: String,
    val houseNumber: String,
    val apartmentNumber: String,
    val floor: Int,
    val postCode: Int,
    val city: String,
    val country: String
)